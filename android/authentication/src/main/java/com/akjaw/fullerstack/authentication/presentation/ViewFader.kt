package com.akjaw.fullerstack.authentication.presentation

import android.animation.Animator
import android.view.View

class ViewFader (private var views: List<View>?) {

    fun fadeOutViews(durationMilliseconds: Long = 300) {
        if (areViewsShown() == true) {
            this.views?.forEach { it.fadeOut(durationMilliseconds) }
        }
    }

    fun fadeInViews(durationMilliseconds: Long = 300) {
        if (areViewsHidden() == true) {
            this.views?.forEach { it.fadeIn(durationMilliseconds) }
        }
    }
    
    fun destroy() {
        views = null
    }

    private fun areViewsShown(): Boolean? = areViewsHidden()?.not()

    private fun areViewsHidden(): Boolean? = views?.none { it.visibility == View.VISIBLE }

    private fun View.fadeIn(durationMilliseconds: Long) {
        fade(0f, 1f, durationMilliseconds)
    }

    private fun View.fadeOut(durationMilliseconds: Long) {
        val listener = object : SimpleAnimatorListener {
            override fun onAnimationEnd(animation: Animator?) {
                visibility = View.GONE
            }
        }
        fade(1f, 0f, durationMilliseconds, listener)
    }

    private fun View.fade(
        startingAlpha: Float,
        endAlpha: Float,
        durationMilliseconds: Long,
        listener: SimpleAnimatorListener? = null
    ) {
        alpha = startingAlpha
        visibility = View.VISIBLE
        animate()
            .alpha(endAlpha)
            .setDuration(durationMilliseconds)
            .setListener(listener)
    }
}
