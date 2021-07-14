package com.strawhat.mvidemo.mvi.feature

sealed class Effect {
    data class CreateTransactionEffect(val type: TransactionFeature.TransactionType) : Effect()
    data class AccountSelectedEffect(val position: Int) : Effect()
    data class ServiceSelectedEffect(val position: Int) : Effect()
    data class PaymentTypeSelectedEffect(val paymentType: TransactionFeature.PaymentType) : Effect()
    data class LimitChangedEffect(val limit: Int) : Effect()
    data class OnNextClickedEffect(val nextStep: TransactionFeature.TransactionStep) : Effect()
    data class DaySelectedEffect(val position: Int) : Effect()
    object ConfirmClickedEffect : Effect()
    data class BackPressedEffect(val prevStep: TransactionFeature.TransactionStep?) : Effect()
}