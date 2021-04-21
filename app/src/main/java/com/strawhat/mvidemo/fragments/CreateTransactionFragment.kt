package com.strawhat.mvidemo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.strawhat.mvidemo.R
import com.strawhat.mvidemo.databinding.FragmentTransactionBinding
import com.strawhat.mvidemo.vms.TransactionVM
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * [Fragment]  for creating transaction
 */
class CreateTransactionFragment : Fragment() {

    private val viewModel: TransactionVM by activityViewModels()

    private var _binding: FragmentTransactionBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.accountSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.accountSelected(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.accountSelected(0)
            }
        }

        binding.serviceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.serviceSelected(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.serviceSelected(0)
            }
        }

        binding.rgPaymentType.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbMonthly -> {
                    viewModel.paymentTypeSelected(TransactionVM.PaymentType.MONTHLY)

                }
                R.id.rbWeekly -> {
                    viewModel.paymentTypeSelected(TransactionVM.PaymentType.WEEKLY)

                }
                R.id.rbUntilFurtherNotice -> {
                    viewModel.paymentTypeSelected(TransactionVM.PaymentType.PERMANENT)
                }
            }
        }

        binding.etLimit.addTextChangedListener {
            viewModel.limitChanged(it.toString().toInt())
        }

        binding.btnNext.setOnClickListener {
            viewModel.onNextClicked()
        }
        viewModel.viewState.observe(viewLifecycleOwner, {
            updateView(it)
        })
        viewModel.eventsFlow
            .flowWithLifecycle(this.lifecycle, Lifecycle.State.STARTED)
            .onEach { event ->
                when (event) {
                    TransactionVM.Event.NavigateToDateFragment -> {
                        val action = CreateTransactionFragmentDirections.actionCreateTransactionFragmentToSelectDateFragment()
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}