package com.strawhat.mvidemo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.strawhat.mvidemo.R
import com.strawhat.mvidemo.databinding.FragmentConfirmationBinding
import com.strawhat.mvidemo.vms.TransactionVM
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


/**
 * [Fragment] for confirming transaction
 */
class ConfirmationFragment : Fragment() {

    private val viewModel by activityViewModels<TransactionVM>()

    private var _binding: FragmentConfirmationBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentConfirmationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnConfirm.setOnClickListener {
            viewModel.onConfirmClicked()
        }
        binding.btnConfirm.setOnClickListener {
            viewModel.onConfirmClicked()
        }
        viewModel.viewState.observe(viewLifecycleOwner, {
            updateView(it)
        })
        viewModel.eventsFlow
            .flowWithLifecycle(this.lifecycle, Lifecycle.State.STARTED)
            .onEach { event ->
                when (event) {
                    TransactionVM.Event.NavigateToStart -> {
                        val action = ConfirmationFragmentDirections.actionConfirmTransactionFragmentToHomeFragment()
                        findNavController().navigate(action)
                    }
                    else -> {
                        // TODO: What about other navigation requests?
                    }
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun updateView(viewState: TransactionVM.State) {
        when (viewState.transactionType) {
            TransactionVM.TransactionType.SOMEONE_ELSE -> {
                activity?.title = getString(R.string.transferToSomeoneElse)
            }
            TransactionVM.TransactionType.OWN -> {
                activity?.title = getString(R.string.transferToOwnAccount)
            }
        }
        displayTransactionDetails(viewState)
    }

    private fun displayTransactionDetails(state: TransactionVM.State) {
        binding.tvTransactionDetails.text =
            getString(R.string.transaction_details_template, state.paymentType, state.service, state.account, state.startDate, state.limit)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}