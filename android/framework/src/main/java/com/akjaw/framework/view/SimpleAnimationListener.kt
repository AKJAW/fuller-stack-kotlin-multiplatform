package com.akjaw.framework.view

import android.view.View
import android.view.animation.Animation

inline fun Animation.setOnAnimationEnd(
    crossinline onAnimationEnd: (p0: Animation?) -> Unit
): Animation.AnimationListener {
    val listener = object : Animation.AnimationListener {
        override fun onAnimationStart(p0: Animation?) {
            /* Empty */
        }

        override fun onAnimationEnd(p0: Animation?) {
            onAnimationEnd(p0)
        }

        override fun onAnimationRepeat(p0: Animation?) {
            /* Empty */
        }
    }

    setAnimationListener(listener)

    return listener
}
