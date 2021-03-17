package com.strawhat.mvidemo.model

interface Service {

    fun createTransaction(request: CreateTransactionRequest): CreateTransactionResult

    fun confirmTransaction(request: ConfirmTransactionRequest): ConfirmTransactionResult
}