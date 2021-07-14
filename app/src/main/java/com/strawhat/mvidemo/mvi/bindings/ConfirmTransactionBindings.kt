package com.strawhat.mvidemo.mvi.bindings

import com.badoo.binder.named
import com.badoo.binder.using
import com.badoo.mvicore.android.AndroidBindings
import com.strawhat.mvidemo.fragments.ConfirmationFragment
import com.strawhat.mvidemo.mvi.ViewModelTransformer
import com.strawhat.mvidemo.mvi.event.UiEventTransformer
import com.strawhat.mvidemo.mvi.feature.TransactionFeature


class ConfirmTransactionBindings(
    view: ConfirmationFragment,
    private val transactionFeature: TransactionFeature
) : AndroidBindings<ConfirmationFragment>(view) {

    override fun setup(view: ConfirmationFragment) {
        binder.bind(transactionFeature to view using ViewModelTransformer() named "ConfirmationFragment.ViewModels")
        binder.bind(view to transactionFeature using UiEventTransformer())
    }

}