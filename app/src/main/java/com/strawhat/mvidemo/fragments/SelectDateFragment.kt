package com.strawhat.mvidemo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.badoo.mvicore.modelWatcher
import com.strawhat.mvidemo.R
import com.strawhat.mvidemo.common.ObservableSourceFragment
import com.strawhat.mvidemo.databinding.FragmentSelectDateBinding
import com.strawhat.mvidemo.mvi.bindings.SelectDateBindings
import com.strawhat.mvidemo.mvi.event.UiEvent
import com.strawhat.mvidemo.mvi.feature.TransactionFeature
import com.strawhat.mvidemo.mvi.feature.ViewModel
import io.reactivex.functions.Consumer

/**
 *  [Fragment] for selecting date
 */
class SelectDateFragment : ObservableSourceFragment<UiEvent>(), Consumer<ViewModel> {

    private lateinit var bindings: SelectDateBindings

    private var _binding: FragmentSelectDateBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onNext(UiEvent.BackPressed)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSelectDateBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val watcher = modelWatcher<ViewModel> {
        watch(ViewModel::paymentType) { paymentType ->
            when (paymentType) {
                TransactionFeature.PaymentType.MONTHLY -> {
                    val adapter = ArrayAdapter.createFromResource(
                        requireContext(),
                        R.array.months_array,
                        android.R.layout.simple_spinner_item
                    )
                    binding.spSelectDay.adapter = adapter
                }
                TransactionFeature.PaymentType.WEEKLY -> {
                    val adapter = ArrayAdapter.createFromResource(
                        requireContext(),
                        R.array.week_days_array,
                        android.R.layout.simple_spinner_item
                    )
                    binding.spSelectDay.adapter = adapter

                }
                TransactionFeature.PaymentType.PERMANENT -> {

                }
            }
            if (paymentType == TransactionFeature.PaymentType.MONTHLY ||
                paymentType == TransactionFeature.PaymentType.WEEKLY
            ) {
                binding.spSelectDay.isVisible = true
                binding.selectDay.text = getString(R.string.select_start_date)
            } else {
                binding.spSelectDay.isVisible = false
                binding.selectDay.text = getString(R.string.automatic_date)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindings = SelectDateBindings(this, TransactionFeature)
        binding.spSelectDay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                onNext(UiEvent.DaySelected(position))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                onNext(UiEvent.DaySelected(0))
            }
        }
        binding.btnNext.setOnClickListener {
            onNext(UiEvent.OnNextClicked)
        }
        bindings.setup(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun accept(viewModel: ViewModel) {
        watcher.invoke(viewModel)
    }
}