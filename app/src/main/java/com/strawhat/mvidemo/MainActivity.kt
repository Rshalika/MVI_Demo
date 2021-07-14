package com.strawhat.mvidemo

import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import com.strawhat.mvidemo.common.ObservableSourceActivity
import com.strawhat.mvidemo.databinding.ActivityMainBinding
import com.strawhat.mvidemo.fragments.ConfirmationFragmentDirections
import com.strawhat.mvidemo.fragments.CreateTransactionFragmentDirections
import com.strawhat.mvidemo.fragments.HomeFragmentDirections
import com.strawhat.mvidemo.fragments.SelectDateFragmentDirections
import com.strawhat.mvidemo.mvi.bindings.MainActivityBindings
import com.strawhat.mvidemo.mvi.event.MainActivityNewsListener
import com.strawhat.mvidemo.mvi.event.PageChangeNews
import com.strawhat.mvidemo.mvi.event.UiEvent
import com.strawhat.mvidemo.mvi.feature.TransactionFeature

class MainActivity : ObservableSourceActivity<UiEvent>() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bindings: MainActivityBindings


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = MainActivityBindings(this, TransactionFeature, MainActivityNewsListener {
            when (it) {
                PageChangeNews.NavigateToCreateTransactionFragment -> {
                    val action = HomeFragmentDirections.actionHomeFragmentToCreateTransactionFragment()
                    findNavController(this, R.id.nav_host_fragment).navigate(action)
                }
                PageChangeNews.NavigateToConfirmFragment -> {
                    val action = SelectDateFragmentDirections.actionSelectDateFragmentToConfirmTransactionFragment()
                    findNavController(this, R.id.nav_host_fragment).navigate(action)
                }
                PageChangeNews.NavigateToDateFragment -> {
                    val action = CreateTransactionFragmentDirections.actionCreateTransactionFragmentToSelectDateFragment()
                    findNavController(this, R.id.nav_host_fragment).navigate(action)
                }
                PageChangeNews.NavigateToStart -> {
                    val action = ConfirmationFragmentDirections.actionConfirmTransactionFragmentToHomeFragment()
                    findNavController(this, R.id.nav_host_fragment).navigate(action)
                }
                PageChangeNews.BackToConfirmFragment -> {

                }
                PageChangeNews.BackToCreateTransactionFragment -> {
                    val action = SelectDateFragmentDirections.actionSelectDateFragmentToCreateTransactionFragment()
                    findNavController(this, R.id.nav_host_fragment).navigate(action)
                }
                PageChangeNews.BackToDateFragment -> {
                    val action = ConfirmationFragmentDirections.actionConfirmTransactionFragmentToSelectDateFragment()
                    findNavController(this, R.id.nav_host_fragment).navigate(action)
                }
                PageChangeNews.BackToStart -> {
                    val action = CreateTransactionFragmentDirections.actionCreateTransactionFragmentToHomeFragment()
                    findNavController(this, R.id.nav_host_fragment).navigate(action)
                }
            }
        })
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)
        bindings.setup(this)

    }
}