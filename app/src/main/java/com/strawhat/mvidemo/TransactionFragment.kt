package com.strawhat.mvidemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TransactionFragment : Fragment() {

    private val viewModel: TransactionVM by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val accountSpinner: Spinner = view.findViewById(R.id.accountSpinner)
        accountSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.accountSelected(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.accountSelected(0)
            }
        }

        val serviceSpinner: Spinner = view.findViewById(R.id.serviceSpinner)
        serviceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.serviceSelected(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.serviceSelected(0)
            }
        }

        val paymentType = view.findViewById<RadioGroup>(R.id.rgPaymentType)
        paymentType.setOnCheckedChangeListener { _, checkedId ->
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

        val limit = view.findViewById<EditText>(R.id.etLimit)
        limit.addTextChangedListener {
            viewModel.limitChanged(it.toString().toInt())
        }

        view.findViewById<Button>(R.id.btnNext).setOnClickListener {
            viewModel.onNextClicked()
        }
    }
}