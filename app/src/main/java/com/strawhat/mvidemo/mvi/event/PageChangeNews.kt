package com.strawhat.mvidemo.mvi.event

sealed class PageChangeNews {
    object NavigateToCreateTransactionFragment : PageChangeNews()
    object NavigateToDateFragment : PageChangeNews()
    object NavigateToConfirmFragment : PageChangeNews()
    object NavigateToStart : PageChangeNews()

    object BackToCreateTransactionFragment : PageChangeNews()
    object BackToDateFragment : PageChangeNews()
    object BackToConfirmFragment : PageChangeNews()
    object BackToStart : PageChangeNews()
}