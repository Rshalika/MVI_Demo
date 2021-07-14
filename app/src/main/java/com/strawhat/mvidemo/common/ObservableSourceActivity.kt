package com.strawhat.mvidemo.common

import androidx.appcompat.app.AppCompatActivity
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject

abstract class ObservableSourceActivity<T> : ObservableSource<T>, AppCompatActivity() {

    private val source = PublishSubject.create<T>()

    protected fun onNext(t: T) {
        source.onNext(t)
    }

    override fun subscribe(observer: Observer<in T>) {
        source.subscribe(observer)
    }
}
