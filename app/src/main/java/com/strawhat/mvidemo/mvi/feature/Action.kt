package com.strawhat.mvidemo.mvi.feature

sealed class Action {
    data class Execute(val wish: Wish) : Action()
}