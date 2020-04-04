package com.example.gamechangedemo.helper

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RxBusImpl : RxBus{

    private val bus:PublishSubject<Any> = PublishSubject.create()

    override fun  send(o: Any) {
          bus.onNext(o)
    }

    override fun <T> listen(eventType: Class<T>): Observable<T> {
        return  bus.ofType(eventType)
    }

}