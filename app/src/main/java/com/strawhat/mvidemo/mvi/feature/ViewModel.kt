package com.strawhat.mvidemo.mvi.feature

import java.util.*

data class ViewModel(
    val transactionType: TransactionFeature.TransactionType,
    val transactionStep: TransactionFeature.TransactionStep,
    val paymentType: TransactionFeature.PaymentType = TransactionFeature.PaymentType.MONTHLY,
    val service: Int? = null,
    val account: Int? = null,
    val limit: Int? = null,
    val startDate: Date? = null,
    val error: String? = null
)