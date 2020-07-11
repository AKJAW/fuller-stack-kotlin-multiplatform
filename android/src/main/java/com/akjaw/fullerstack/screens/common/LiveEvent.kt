package com.akjaw.fullerstack.screens.common

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

//TODO test memory leaks
class LiveEvent<T> {
    private val observers = mutableMapOf<LifecycleOwner, (T) -> Unit>()

    fun observe(owner: LifecycleOwner, onChanged: (T) -> Unit) {
        if(owner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
            return
        }
        LiveEventLifecycleObserver(owner)
        observers[owner] = onChanged
    }

    fun postValue(data: T) {
        observers.keys.forEach { owner ->
            val lifecycle = owner.lifecycle
            if(lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                observers[owner]?.invoke(data)
            }
        }
    }

    private inner class LiveEventLifecycleObserver(private val owner: LifecycleOwner) : LifecycleObserver {
        init {
            owner.lifecycle.addObserver(this)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            observers.remove(owner)
            owner.lifecycle.removeObserver(this)
        }
    }
}

