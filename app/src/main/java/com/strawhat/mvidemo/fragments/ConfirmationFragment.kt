package com.strawhat.mvidemo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.strawhat.mvidemo.R
import com.strawhat.mvidemo.common.ObservableSourceFragment
import com.strawhat.mvidemo.databinding.FragmentConfirmationBinding
import com.strawhat.mvidemo.mvi.bindings.ConfirmTransactionBindings
import com.strawhat.mvidemo.mvi.event.UiEvent
import com.strawhat.mvidemo.mvi.feature.TransactionFeature
import com.strawhat.mvidemo.mvi.feature.ViewModel
import io.reactivex.functions.Consumer

/**
 * [Fragment] for confirming transaction
 */
class ConfirmationFragment : ObservableSourceFragment<UiEvent>(), Consumer<ViewModel> {

    private lateinit var bindings: ConfirmTransactionBindings

    private var _binding: FragmentConfirmationBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentConfirmationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onNext(UiEvent.BackPressed)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindings = ConfirmTransactionBindings(this, TransactionFeature)
        binding.btnConfirm.setOnClickListener {
            onNext(UiEvent.ConfirmClicked)
        }
        bindings.setup(this)
    }

    private fun updateView(viewModel: ViewModel) {
        when (viewModel.transactionType) {
            TransactionFeature.TransactionType.SOMEONE_ELSE -> {
                activity?.title = getString(R.string.transferToSomeoneElse)
            }
            TransactionFeature.TransactionType.OWN -> {
                activity?.title = getString(R.string.transferToOwnAccount)
            }
        }
        displayTransactionDetails(viewModel)
    }

    private fun displayTransactionDetails(state: ViewModel) {
        binding.tvTransactionDetails.text =
            getString(R.string.transaction_details_template, state.paymentType, state.service, state.account, state.startDate, state.limit)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun accept(viewModel: ViewModel) {
        updateView(viewModel)
    }
}