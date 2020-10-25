package com.akjaw.fullerstack.screens.common.main

import android.os.Bundle
import android.view.MenuItem
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.screens.common.base.BaseActivity
import com.akjaw.fullerstack.screens.common.navigation.keys.NotesListScreen
import com.akjaw.fullerstack.screens.common.navigation.keys.ProfileScreen
import com.akjaw.fullerstack.screens.common.navigation.keys.SettingsScreen
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.SimpleStateChanger
import com.zhuinden.simplestack.StateChange
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentStateChanger
import com.zhuinden.simplestackextensions.navigatorktx.backstack

class MainActivity : BaseActivity(), SimpleStateChanger.NavigationHandler {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fragmentStateChanger: DefaultFragmentStateChanger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(::handleNavigationItemSelected)

        fragmentStateChanger = DefaultFragmentStateChanger(supportFragmentManager, R.id.fragment_placeholder)
        Navigator.configure()
            .setStateChanger(SimpleStateChanger(this))
            .install(this, findViewById(R.id.root), History.of(NotesListScreen()))
    }

    private fun handleNavigationItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.home -> {
                backstack.goTo(NotesListScreen())//TODO what do
                true
            }
            R.id.profile -> {
                backstack.goTo(ProfileScreen())
                true
            }
            R.id.settings -> {
                backstack.goTo(SettingsScreen())
                true
            }
            else -> false
        }
    }

    override fun onBackPressed() {
        if (Navigator.onBackPressed(this).not()) {
            super.onBackPressed()
        }
    }

    override fun onNavigationEvent(stateChange: StateChange) {
        fragmentStateChanger.handleStateChange(stateChange)
    }
}
