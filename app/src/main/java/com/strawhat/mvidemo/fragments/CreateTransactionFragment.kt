package com.strawhat.mvidemo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.badoo.mvicore.modelWatcher
import com.strawhat.mvidemo.R
import com.strawhat.mvidemo.common.ObservableSourceFragment
import com.strawhat.mvidemo.databinding.FragmentTransactionBinding
import com.strawhat.mvidemo.mvi.bindings.CreateTransactionBindings
import com.strawhat.mvidemo.mvi.event.UiEvent
import com.strawhat.mvidemo.mvi.feature.TransactionFeature
import com.strawhat.mvidemo.mvi.feature.ViewModel
import io.reactivex.functions.Consumer

/**
 * [Fragment]  for creating transaction
 */
class CreateTransactionFragment : ObservableSourceFragment<UiEvent>(), Consumer<ViewModel> {

    private lateinit var bindings: CreateTransactionBindings

    private var _binding: FragmentTransactionBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
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
        bindings = CreateTransactionBindings(this, TransactionFeature)
        binding.accountSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                onNext(UiEvent.AccountSelected(position))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                onNext(UiEvent.AccountSelected(0))
            }
        }

        binding.serviceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                onNext(UiEvent.ServiceSelected(position))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                onNext(UiEvent.ServiceSelected(0))
            }
        }

        binding.rgPaymentType.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbMonthly -> {
                    onNext(UiEvent.PaymentTypeSelected(TransactionFeature.PaymentType.MONTHLY))
                }
                R.id.rbWeekly -> {
                    onNext(UiEvent.PaymentTypeSelected(TransactionFeature.PaymentType.WEEKLY))

                }
                R.id.rbUntilFurtherNotice -> {
                    onNext(UiEvent.PaymentTypeSelected(TransactionFeature.PaymentType.PERMANENT))
                }
            }
        }

        binding.etLimit.addTextChangedListener {
            onNext(UiEvent.LimitChanged(it.toString().toInt()))
        }

        binding.btnNext.setOnClickListener {
            onNext(UiEvent.OnNextClicked)
        }
        bindings.setup(this)
    }

    private val watcher = modelWatcher<ViewModel> {
        watch(ViewModel::transactionType) { transactionType ->
            when (transactionType) {
                TransactionFeature.TransactionType.SOMEONE_ELSE -> {
                    activity?.title = getString(R.string.transferToSomeoneElse)
                }
                TransactionFeature.TransactionType.OWN -> {
                    activity?.title = getString(R.string.transferToOwnAccount)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun accept(viewModel: ViewModel) {
        watcher.invoke(viewModel)
    }
}