package com.strawhat.mvidemo.mvi.feature

import com.badoo.mvicore.element.Actor
import com.badoo.mvicore.element.NewsPublisher
import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.BaseFeature
import com.strawhat.mvidemo.mvi.event.PageChangeNews
import io.reactivex.Observable
import org.joda.time.DateTime
import java.util.*


object TransactionFeature : BaseFeature<Wish, Action, Effect, State, News>(
    wishToAction = { Action.Execute(it) },
    actor = ActorImpl(),
    initialState = State(),
    reducer = ReducerImpl(),
    newsPublisher = NewsPublisherImpl()
) {

    class ActorImpl : Actor<State, Action, Effect> {
        override fun invoke(state: State, action: Action): Observable<Effect> {
            return Observable.just(
                when (action) {
                    is Action.Execute -> when (action.wish) {
                        is Wish.CreateTransactionWish -> Effect.CreateTransactionEffect(action.wish.type)
                        is Wish.AccountSelectedWish -> Effect.AccountSelectedEffect(action.wish.position)
                        is Wish.ConfirmClickedWish -> Effect.ConfirmClickedEffect
                        is Wish.BackPressedWish -> Effect.BackPressedEffect(getPrevStep(state))
                        is Wish.DaySelectedWish -> Effect.DaySelectedEffect(action.wish.position)
                        is Wish.LimitChangedWish -> Effect.LimitChangedEffect(action.wish.limit)
                        is Wish.OnNextClickedWish -> Effect.OnNextClickedEffect(getNextStep(state))
                        is Wish.PaymentTypeSelectedWish -> Effect.PaymentTypeSelectedEffect(action.wish.transactionType)
                        is Wish.ServiceSelectedWish -> Effect.ServiceSelectedEffect(action.wish.position)
                    }
                }
            )
        }
    }

    private fun getPrevStep(state: State): TransactionStep? {
        return when (state.transactionStep) {
            TransactionStep.CREATE -> TransactionStep.IDLE
            TransactionStep.SELECT_DATE -> TransactionStep.CREATE
            TransactionStep.CONFIRM -> TransactionStep.SELECT_DATE
            TransactionStep.IDLE -> null
        }
    }


    private fun getStartDate(position: Int): Date? {
        if (state.paymentType == PaymentType.WEEKLY) {
            return DateTime().plusDays(position).toDate()
        } else if (state.paymentType == PaymentType.MONTHLY) {
            return DateTime().plusMonths(position).toDate()
        }
        return null
    }


    private fun getNextStep(state: State): TransactionStep {
        return when (state.transactionStep) {
            TransactionStep.CREATE -> TransactionStep.SELECT_DATE
            TransactionStep.SELECT_DATE -> TransactionStep.CONFIRM
            TransactionStep.CONFIRM -> TransactionStep.IDLE
            TransactionStep.IDLE -> TransactionStep.CREATE
        }
    }

    class ReducerImpl : Reducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State {
            return when (effect) {
                is Effect.AccountSelectedEffect -> state.copy(account = effect.position)
                is Effect.ConfirmClickedEffect -> state.copy(transactionStep = TransactionStep.IDLE)
                is Effect.CreateTransactionEffect -> state.copy(transactionStep = TransactionStep.CREATE, transactionType = effect.type)
                is Effect.DaySelectedEffect -> state.copy(startDate = getStartDate(effect.position))
                is Effect.LimitChangedEffect -> state.copy(limit = effect.limit)
                is Effect.OnNextClickedEffect -> state.copy(transactionStep = effect.nextStep)
                is Effect.PaymentTypeSelectedEffect -> state.copy(paymentType = effect.paymentType)
                is Effect.ServiceSelectedEffect -> state.copy(service = effect.position)
                is Effect.BackPressedEffect -> state.copy(transactionStep = effect.prevStep ?: state.transactionStep)
            }
        }

    }

    class NewsPublisherImpl : NewsPublisher<Action, Effect, State, News> {
        override fun invoke(action: Action, effect: Effect, state: State): News? {
            return when (effect) {
                is Effect.ConfirmClickedEffect -> News.PageOpened(PageChangeNews.NavigateToStart)
                is Effect.OnNextClickedEffect -> {
                    when (effect.nextStep) {
                        TransactionStep.CREATE -> News.PageOpened(PageChangeNews.NavigateToCreateTransactionFragment)
                        TransactionStep.SELECT_DATE -> News.PageOpened(PageChangeNews.NavigateToDateFragment)
                        TransactionStep.CONFIRM -> News.PageOpened(PageChangeNews.NavigateToConfirmFragment)
                        TransactionStep.IDLE -> News.PageOpened(PageChangeNews.NavigateToStart)
                    }
                }
                is Effect.CreateTransactionEffect -> News.PageOpened(PageChangeNews.NavigateToCreateTransactionFragment)
                is Effect.BackPressedEffect -> {
                    when (effect.prevStep) {
                        TransactionStep.CREATE -> News.PageOpened(PageChangeNews.BackToCreateTransactionFragment)
                        TransactionStep.SELECT_DATE -> News.PageOpened(PageChangeNews.BackToDateFragment)
                        TransactionStep.CONFIRM -> News.PageOpened(PageChangeNews.BackToConfirmFragment)
                        TransactionStep.IDLE -> News.PageOpened(PageChangeNews.BackToStart)
                        null -> null
                    }
                }
                else -> null
            }
        }

    }

    enum class PaymentType { MONTHLY, WEEKLY, PERMANENT }
    enum class TransactionType { SOMEONE_ELSE, OWN }
    enum class TransactionStep { CREATE, SELECT_DATE, CONFIRM, IDLE }
}




