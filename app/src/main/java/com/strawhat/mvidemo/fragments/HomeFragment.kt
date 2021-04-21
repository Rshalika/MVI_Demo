package com.strawhat.mvidemo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.strawhat.mvidemo.R
import com.strawhat.mvidemo.databinding.FragmentHomeBinding
import com.strawhat.mvidemo.vms.TransactionVM
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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
        activity?.title = getString(R.string.app_name)
        binding.btnCreateTransactionToSomeoneElse.setOnClickListener {
            viewModel.createTransactionToSomeoneElse()
        }
        binding.btnCreateTransactionToOwnAccount.setOnClickListener {
            viewModel.createTransactionToOwnAccount()
        }
        viewModel.eventsFlow
            .flowWithLifecycle(this.lifecycle, Lifecycle.State.STARTED)
            .onEach { event ->
                when (event) {
                    TransactionVM.Event.NavigateToCreateTransactionFragment -> {
                        val action = HomeFragmentDirections.actionHomeFragmentToCreateTransactionFragment()
                        findNavController().navigate(action)
                    }
                    else -> {
                        // TODO: What about other navigation requests?
                    }
                }
            }
            .launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

