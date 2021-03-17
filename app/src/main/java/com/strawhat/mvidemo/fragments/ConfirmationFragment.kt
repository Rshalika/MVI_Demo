package com.strawhat.mvidemo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.strawhat.mvidemo.R
import com.strawhat.mvidemo.TransactionDetails
import com.strawhat.mvidemo.databinding.FragmentConfirmationBinding
import com.strawhat.mvidemo.vms.TransactionVM


/**
 * [Fragment] for confirming transaction
 */
class ConfirmationFragment : Fragment() {

    private val viewModel by activityViewModels<TransactionVM>()

    private var _binding: FragmentConfirmationBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentConfirmationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnConfirm.setOnClickListener {
            viewModel.onConfirmClicked()
        }
    }

    fun displayTransactionDetails(transactionDetails: TransactionDetails) {
        binding.tvTransactionDetails.text = transactionDetails.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        const val TRANSACTION_DETAILS: String = "TRANSACTION_DETAILS"

        fun newInstance(transactionDetails: TransactionDetails): ConfirmationFragment {
            val fragment = ConfirmationFragment()
            fragment.arguments = Bundle().apply {
                putSerializable(TRANSACTION_DETAILS, transactionDetails)
            }
            return fragment
        }
    }
}