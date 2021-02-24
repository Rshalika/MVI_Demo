package com.strawhat.mvidemo

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class TransactionVM(application: Application) : AndroidViewModel(application) {
    fun onNextClicked() {}
    fun serviceSelected(position: Int) {}
    fun accountSelected(position: Int) {}
    fun paymentTypeSelected(paymentType: PaymentType) {}
    fun limitChanged(limit: Int) {}
    enum class PaymentType { MONTHLY, WEEKLY, PERMANENT }
}