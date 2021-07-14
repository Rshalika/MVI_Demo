package com.strawhat.mvidemo.mvi.feature

import com.strawhat.mvidemo.mvi.event.PageChangeNews


sealed class News {
    data class PageOpened(val page: PageChangeNews) : News()
}