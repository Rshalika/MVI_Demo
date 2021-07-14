package com.strawhat.mvidemo.mvi.bindings

import com.badoo.binder.named
import com.badoo.mvicore.android.AndroidBindings
import com.strawhat.mvidemo.MainActivity
import com.strawhat.mvidemo.mvi.event.MainActivityNewsListener
import com.strawhat.mvidemo.mvi.feature.TransactionFeature


class MainActivityBindings(
    view: MainActivity,
    private val transactionFeature: TransactionFeature,
    private val mainActivityNewsListener: MainActivityNewsListener
) : AndroidBindings<MainActivity>(view) {

    override fun setup(view: MainActivity) {
        binder.bind(transactionFeature.news to mainActivityNewsListener named "MainActivity.News")
    }
}