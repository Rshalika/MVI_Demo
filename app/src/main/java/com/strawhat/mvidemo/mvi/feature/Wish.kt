package com.strawhat.mvidemo.mvi.feature

sealed class Wish {
    data class CreateTransactionWish(val type: TransactionFeature.TransactionType) : Wish()
    data class AccountSelectedWish(val position: Int) : Wish()
    data class ServiceSelectedWish(val position: Int) : Wish()
    data class PaymentTypeSelectedWish(val transactionType: TransactionFeature.PaymentType) : Wish()
    data class LimitChangedWish(val limit: Int) : Wish()
    object OnNextClickedWish : Wish()
    data class DaySelectedWish(val position: Int) : Wish()
    object ConfirmClickedWish : Wish()
    object BackPressedWish : Wish()
}