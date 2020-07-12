package com.akjaw.fullerstack

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.akjaw.fullerstack.screens.common.LiveEvent

fun <T> LiveEvent<T>.testObserve() : () -> Boolean {
    var wasCalled = false

    val owner = LifecycleOwner {
        object : Lifecycle() {
            override fun addObserver(observer: LifecycleObserver) {}
            override fun removeObserver(observer: LifecycleObserver) {}
            override fun getCurrentState(): State = State.STARTED
        }
    }
    this.observe(owner) { wasCalled = true }

    return { wasCalled }
}
