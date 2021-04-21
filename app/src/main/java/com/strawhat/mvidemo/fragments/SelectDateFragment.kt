package com.strawhat.mvidemo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.strawhat.mvidemo.R
import com.strawhat.mvidemo.databinding.FragmentSelectDateBinding
import com.strawhat.mvidemo.vms.TransactionVM
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 *  [Fragment] for selecting date
 */
class SelectDateFragment : Fragment() {

    val viewModel by activityViewModels<TransactionVM>()

    private var _binding: FragmentSelectDateBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSelectDateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.spSelectDay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.onDaySelected(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.onDaySelected(0)
            }
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
                    TransactionVM.Event.NavigateToConfirmFragment -> {
                        val action = SelectDateFragmentDirections.actionSelectDateFragmentToConfirmTransactionFragment()
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
        when (viewState.paymentType) {
            TransactionVM.PaymentType.MONTHLY -> {
                val adapter = ArrayAdapter.createFromResource(
                    requireContext(),
                    R.array.months_array,
                    android.R.layout.simple_spinner_item
                )
                binding.spSelectDay.adapter = adapter
            }
            TransactionVM.PaymentType.WEEKLY -> {
                val adapter = ArrayAdapter.createFromResource(
                    requireContext(),
                    R.array.week_days_array,
                    android.R.layout.simple_spinner_item
                )
                binding.spSelectDay.adapter = adapter

            }
            TransactionVM.PaymentType.PERMANENT -> {

            }
        }
        if (viewState.paymentType == TransactionVM.PaymentType.MONTHLY ||
            viewState.paymentType == TransactionVM.PaymentType.WEEKLY
        ) {
            binding.spSelectDay.isVisible = true
            binding.selectDay.text = getString(R.string.select_start_date)
        } else {
            binding.spSelectDay.isVisible = false
            binding.selectDay.text = getString(R.string.automatic_date)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}