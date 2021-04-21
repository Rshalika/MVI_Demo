package com.strawhat.mvidemo.vms

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import java.util.*

class TransactionVM(application: Application) : AndroidViewModel(application) {

    private var state = State(TransactionType.OWN, TransactionStep.IDLE)

    private var _viewState = MutableLiveData<State>()

    val viewState: LiveData<State> = _viewState

    private val eventChannel = Channel<Event>(Channel.BUFFERED)

    val eventsFlow = eventChannel.receiveAsFlow()

    fun onNextClicked() {
        goToNextStep()
    }


    fun serviceSelected(position: Int) {
        emit(state.copy(service = position))
    }

    fun accountSelected(position: Int) {
        emit(state.copy(account = position))
    }

    fun paymentTypeSelected(paymentType: PaymentType) {
        emit(state.copy(paymentType = paymentType))
    }

    fun limitChanged(limit: Int) {
        emit(state.copy(limit = limit))
    }

    fun onDaySelected(position: Int) {
        emit(state.copy(startDate = getStartDate(position)))
    }

    private fun getStartDate(position: Int): Date? {
        if (state.paymentType == PaymentType.WEEKLY) {
            return DateTime().plusDays(position).toDate()
        } else if (state.paymentType == PaymentType.MONTHLY) {
            return DateTime().plusMonths(position).toDate()
        }
        return null
    }

    fun onConfirmClicked() {
        emit(state.copy(transactionStep = TransactionStep.IDLE))
        viewModelScope.launch {
            eventChannel.send(Event.NavigateToStart)
        }
    }

    fun createTransactionToSomeoneElse() {
        emit(state.copy(transactionType = TransactionType.SOMEONE_ELSE, transactionStep = TransactionStep.CREATE))
        viewModelScope.launch {
            eventChannel.send(Event.NavigateToCreateTransactionFragment)
        }
    }

    fun createTransactionToOwnAccount() {
        emit(state.copy(transactionType = TransactionType.OWN, transactionStep = TransactionStep.CREATE))
        viewModelScope.launch {
            eventChannel.send(Event.NavigateToCreateTransactionFragment)
        }
    }


    private fun goToNextStep() {
        viewModelScope.launch {
            when (state.transactionStep) {
                TransactionStep.CREATE -> {
                    emit(state.copy(transactionStep = TransactionStep.SELECT_DATE))
                    eventChannel.send(Event.NavigateToDateFragment)
                }
                TransactionStep.SELECT_DATE -> {
                    emit(state.copy(transactionStep = TransactionStep.CONFIRM))
                    eventChannel.send(Event.NavigateToConfirmFragment)
                }
                TransactionStep.CONFIRM -> {
                    // TODO
                }
                TransactionStep.IDLE -> {
                    // TODO
                }
            }
        }
    }

    private fun emit(state: State) {
        this.state = state
        _viewState.value = this.state
    }

    enum class PaymentType { MONTHLY, WEEKLY, PERMANENT }
    enum class TransactionType { SOMEONE_ELSE, OWN }
    enum class TransactionStep { CREATE, SELECT_DATE, CONFIRM, IDLE }

    data class State(
        val transactionType: TransactionType,
        val transactionStep: TransactionStep,
        val paymentType: PaymentType = PaymentType.MONTHLY,
        val service: Int? = null,
        val account: Int? = null,
        val limit: Int? = null,
        val startDate: Date? = null,
        val error: String? = null
    )

    sealed class Event {
        object NavigateToCreateTransactionFragment : Event()
        object NavigateToDateFragment : Event()
        object NavigateToConfirmFragment : Event()
        object NavigateToStart : Event()
    }
}