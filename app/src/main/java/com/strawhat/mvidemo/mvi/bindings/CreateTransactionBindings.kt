package com.strawhat.mvidemo.mvi.bindings

import com.badoo.binder.named
import com.badoo.binder.using
import com.badoo.mvicore.android.AndroidBindings
import com.strawhat.mvidemo.fragments.CreateTransactionFragment
import com.strawhat.mvidemo.mvi.ViewModelTransformer
import com.strawhat.mvidemo.mvi.event.UiEventTransformer
import com.strawhat.mvidemo.mvi.feature.TransactionFeature


class CreateTransactionBindings(
    view: CreateTransactionFragment,
    private val transactionFeature: TransactionFeature
) : AndroidBindings<CreateTransactionFragment>(view) {

    override fun setup(view: CreateTransactionFragment) {
        binder.bind(transactionFeature to view using ViewModelTransformer() named "CreateTransactionFragment.ViewModels")
        binder.bind(view to transactionFeature using UiEventTransformer())
    }

}