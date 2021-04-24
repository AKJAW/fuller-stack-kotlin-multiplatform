package com.akjaw.fullerstack.screens.common.main

import android.os.Bundle
import androidx.activity.viewModels
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.screens.common.base.BaseActivity
import com.akjaw.fullerstack.screens.common.composition.mainActivityModule
import com.akjaw.fullerstack.screens.common.navigation.MultiStack
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.kodein.di.DI
import org.kodein.di.direct
import org.kodein.di.instance

class MainActivity : BaseActivity() {

    override val di: DI by DI.lazy {
        extend(super.di)
        import(mainActivityModule)
    }

    private val mainActivityViewModel: MainActivityViewModel by viewModels { di.direct.instance() }
    private val multiStack: MultiStack by instance()
    private val bottomNavigationHelper: BottomNavigationHelper by instance()
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        if (savedInstanceState == null) {
            mainActivityViewModel.startNotesSocket()
        }
        bottomNavigationHelper.initialize(supportFragmentManager, bottomNavigationView)
    }

    override fun onDestroy() {
        super.onDestroy()
        bottomNavigationHelper.destroy()
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
