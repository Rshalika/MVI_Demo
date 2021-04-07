package com.strawhat.mvidemo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.strawhat.mvidemo.databinding.FragmentHomeBinding
import com.strawhat.mvidemo.vms.TransactionVM

/**
 * [Fragment]  for creating transaction
 */
class HomeFragment : Fragment() {

    private val viewModel by activityViewModels<TransactionVM>()

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCreateTransactionToSomeoneElse.setOnClickListener {
            viewModel.createTransactionToSomeoneElse()
        }
        binding.btnCreateTransactionToOwnAccount.setOnClickListener {
            viewModel.createTransactionToOwnAccount()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

