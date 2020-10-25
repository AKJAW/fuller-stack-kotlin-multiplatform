package com.akjaw.fullerstack.screens.common.main

import android.os.Bundle
import android.view.MenuItem
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.screens.common.base.BaseActivity
import com.akjaw.fullerstack.screens.common.navigation.MultiStack
import com.akjaw.fullerstack.screens.common.navigation.MultiStackFragmentStateChanger
import com.akjaw.fullerstack.screens.common.navigation.keys.MultiStackFragmentKey
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.kodein.di.instance

class MainActivity : BaseActivity() {

    private val multiStack: MultiStack by instance()
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(::handleNavigationItemSelected)

        multiStack.setStateChanger(MultiStackFragmentStateChanger(supportFragmentManager, R.id.fragment_placeholder))
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

    override fun onDestroy() {
        super.onDestroy()
        multiStack.executePendingStateChange()
    }

    override fun onPostResume() {
        super.onPostResume()
        multiStack.resume()
    }

    override fun onPause() {
        super.onPause()
        multiStack.pause()
    }

    override fun onBackPressed() {
        val backstack = multiStack.getSelectedStack()
        if (backstack.goBack().not()) {
            super.onBackPressed()
        }
    }
}
