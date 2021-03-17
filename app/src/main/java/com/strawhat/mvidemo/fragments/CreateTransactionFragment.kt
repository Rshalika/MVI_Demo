package com.strawhat.mvidemo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.strawhat.mvidemo.R
import com.strawhat.mvidemo.databinding.FragmentTransactionBinding
import com.strawhat.mvidemo.vms.TransactionVM

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}