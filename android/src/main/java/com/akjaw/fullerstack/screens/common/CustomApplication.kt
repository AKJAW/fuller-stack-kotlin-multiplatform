package com.akjaw.fullerstack.screens.common

import android.app.Application
import com.akjaw.fullerstack.dependencyinjection.modules.basic
import dependencyinjection.common
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.androidXModule

class CustomApplication : Application(), DIAware {
    override val di by DI.lazy {
        import(androidXModule(this@CustomApplication))
        import(common)
        import(basic)
    }

    override fun onCreate() {
        super.onCreate()

//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
}
