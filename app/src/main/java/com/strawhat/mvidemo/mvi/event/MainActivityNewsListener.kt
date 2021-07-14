package com.strawhat.mvidemo.mvi.event

import com.strawhat.mvidemo.mvi.feature.News
import io.reactivex.functions.Consumer


open class MainActivityNewsListener(private val listener: (PageChangeNews) -> Unit) : Consumer<News> {

    override fun accept(news: News) {
        listener.invoke((news as News.PageOpened).page)
    }

}
