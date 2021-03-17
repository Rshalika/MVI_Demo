package com.strawhat.mvidemo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.strawhat.mvidemo.databinding.FragmentSelectDateBinding
import com.strawhat.mvidemo.vms.TransactionVM

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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}