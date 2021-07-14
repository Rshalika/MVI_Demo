package com.strawhat.mvidemo.mvi.feature

import java.util.*

data class State(
    val transactionType: TransactionFeature.TransactionType = TransactionFeature.TransactionType.OWN,
    val transactionStep: TransactionFeature.TransactionStep = TransactionFeature.TransactionStep.IDLE,
    val paymentType: TransactionFeature.PaymentType = TransactionFeature.PaymentType.MONTHLY,
    val service: Int? = null,
    val account: Int? = null,
    val limit: Int? = null,
    val startDate: Date? = null,
    val error: String? = null
)
