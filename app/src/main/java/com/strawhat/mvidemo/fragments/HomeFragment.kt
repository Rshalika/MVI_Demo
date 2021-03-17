package com.strawhat.mvidemo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.strawhat.mvidemo.R
import com.strawhat.mvidemo.databinding.FragmentHomeBinding
import com.strawhat.mvidemo.databinding.FragmentTransactionBinding
import com.strawhat.mvidemo.vms.TransactionVM

/**
 * [Fragment]  for creating transaction
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

