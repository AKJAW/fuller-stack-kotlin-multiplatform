package com.akjaw.fullerstack.screens.common.main

import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.screens.common.navigation.MultiStack
import com.akjaw.fullerstack.screens.common.navigation.MultiStackFragmentStateChanger
import com.akjaw.fullerstack.screens.common.navigation.keys.MultiStackFragmentKey
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationHelper(
    private val multiStack: MultiStack
) {
    private var view: BottomNavigationView? = null

    fun initialize(fragmentManager: FragmentManager, view: BottomNavigationView) {
        this.view = view
        multiStack.setStateChanger(MultiStackFragmentStateChanger(fragmentManager, R.id.fragment_placeholder))
        view.setOnNavigationItemSelectedListener(::handleNavigationItemSelected)
    }

    private fun handleNavigationItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.home -> {
                multiStack.setSelectedStack(MultiStackFragmentKey.RootFragments.HOME.name)
                true
            }
            R.id.profile -> {
                multiStack.setSelectedStack(MultiStackFragmentKey.RootFragments.PROFILE.name)
                true
            }
            R.id.settings -> {
                multiStack.setSelectedStack(MultiStackFragmentKey.RootFragments.SETTINGS.name)
                true
            }
            else -> false
        }
    }

    fun destroy() {
        view = null
    }

    fun show() {
        view?.animate()?.translationY(0f)
    }

    fun hide() {
        val height = view?.height ?: 0
        view?.animate()?.translationY(height.toFloat())
    }
}
