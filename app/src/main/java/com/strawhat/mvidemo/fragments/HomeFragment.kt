package com.strawhat.mvidemo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.strawhat.mvidemo.R
import com.strawhat.mvidemo.common.ObservableSourceFragment
import com.strawhat.mvidemo.databinding.FragmentHomeBinding
import com.strawhat.mvidemo.mvi.bindings.HomePageBindings
import com.strawhat.mvidemo.mvi.event.UiEvent
import com.strawhat.mvidemo.mvi.feature.TransactionFeature
import com.strawhat.mvidemo.mvi.feature.ViewModel
import io.reactivex.functions.Consumer

/**
 * [Fragment]  for creating transaction
 */
class HomeFragment : ObservableSourceFragment<UiEvent>(), Consumer<ViewModel> {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private lateinit var bindings: HomePageBindings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onNext(UiEvent.BackPressed)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindings = HomePageBindings(this, TransactionFeature)
        activity?.title = getString(R.string.app_name)

        binding.btnCreateTransactionToSomeoneElse.setOnClickListener {
            onNext(UiEvent.TransactionCreateClicked(TransactionFeature.TransactionType.SOMEONE_ELSE))
        }
        binding.btnCreateTransactionToOwnAccount.setOnClickListener {
            onNext(UiEvent.TransactionCreateClicked(TransactionFeature.TransactionType.OWN))
        }
        bindings.setup(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun accept(t: ViewModel?) {
    }
}

