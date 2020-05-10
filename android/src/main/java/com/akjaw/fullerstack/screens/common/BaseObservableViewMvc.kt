package com.akjaw.fullerstack.screens.common

abstract class BaseObservableViewMvc<T>: BaseViewMvc() {

    private val _listeners = mutableSetOf<T>()
    protected val listeners: Set<T> = _listeners

    fun registerListener(listener: T){
        _listeners.add(listener)
    }

    fun unregisterListener(listener: T){
        _listeners.remove(listener)
    }
}
