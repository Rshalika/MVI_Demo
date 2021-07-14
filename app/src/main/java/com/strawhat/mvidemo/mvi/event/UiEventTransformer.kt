package com.strawhat.mvidemo.mvi.event

import com.strawhat.mvidemo.mvi.feature.Wish

class UiEventTransformer : (UiEvent) -> Wish? {
    override fun invoke(event: UiEvent): Wish = when (event) {
        is UiEvent.TransactionCreateClicked -> Wish.CreateTransactionWish(event.type)
        is UiEvent.AccountSelected -> Wish.AccountSelectedWish(event.position)
        is UiEvent.ServiceSelected -> Wish.ServiceSelectedWish(event.position)
        is UiEvent.PaymentTypeSelected -> Wish.PaymentTypeSelectedWish(event.transactionType)
        is UiEvent.LimitChanged -> Wish.LimitChangedWish(event.limit)
        is UiEvent.OnNextClicked -> Wish.OnNextClickedWish
        is UiEvent.DaySelected -> Wish.DaySelectedWish(event.position)
        is UiEvent.ConfirmClicked -> Wish.ConfirmClickedWish
        is UiEvent.BackPressed -> Wish.BackPressedWish
    }
}