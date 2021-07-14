package com.strawhat.mvidemo.mvi.bindings

import com.badoo.binder.named
import com.badoo.binder.using
import com.badoo.mvicore.android.AndroidBindings
import com.strawhat.mvidemo.fragments.SelectDateFragment
import com.strawhat.mvidemo.mvi.ViewModelTransformer
import com.strawhat.mvidemo.mvi.event.UiEventTransformer
import com.strawhat.mvidemo.mvi.feature.TransactionFeature


class SelectDateBindings(
    view: SelectDateFragment,
    private val transactionFeature: TransactionFeature
) : AndroidBindings<SelectDateFragment>(view) {

    override fun setup(view: SelectDateFragment) {
        binder.bind(transactionFeature to view using ViewModelTransformer() named "SelectDateFragment.ViewModels")
        binder.bind(view to transactionFeature using UiEventTransformer())
    }

}