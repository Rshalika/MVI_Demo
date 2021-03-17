package com.strawhat.mvidemo

import java.io.Serializable
import java.util.*

data class TransactionDetails(val account: String, val service: String, val startDate: Date) : Serializable
