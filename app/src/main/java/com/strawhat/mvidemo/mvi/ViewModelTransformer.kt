package com.strawhat.mvidemo.mvi

import com.strawhat.mvidemo.mvi.feature.State
import com.strawhat.mvidemo.mvi.feature.ViewModel


class ViewModelTransformer : (State) -> ViewModel {

    override fun invoke(state: State): ViewModel {
        return ViewModel(
            transactionType = state.transactionType,
            transactionStep = state.transactionStep,
            paymentType = state.paymentType,
            service = state.service,
            account = state.account,
            limit = state.limit,
            startDate = state.startDate,
            error = state.error
        )
    }
}
