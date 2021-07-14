package com.strawhat.mvidemo.mvi.event

import com.strawhat.mvidemo.mvi.feature.TransactionFeature

sealed class UiEvent {
    data class TransactionCreateClicked(val type: TransactionFeature.TransactionType) : UiEvent()
    data class AccountSelected(val position: Int) : UiEvent()
    data class ServiceSelected(val position: Int) : UiEvent()
    data class PaymentTypeSelected(val transactionType: TransactionFeature.PaymentType) : UiEvent()
    data class LimitChanged(val limit: Int) : UiEvent()
    object OnNextClicked : UiEvent()
    data class DaySelected(val position: Int) : UiEvent()
    object ConfirmClicked : UiEvent()
    object BackPressed : UiEvent()
}