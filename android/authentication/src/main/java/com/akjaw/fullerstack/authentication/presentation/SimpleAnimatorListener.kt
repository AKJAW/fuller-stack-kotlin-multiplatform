package com.akjaw.fullerstack.authentication.presentation

import android.animation.Animator

interface SimpleAnimatorListener : Animator.AnimatorListener {
    override fun onAnimationRepeat(animation: Animator?) {
        /* Empty */
    }

    override fun onAnimationEnd(animation: Animator?) {
        /* Empty */
    }

    override fun onAnimationCancel(animation: Animator?) {
        /* Empty */
    }

    override fun onAnimationStart(animation: Animator?) {
        /* Empty */
    }
}
