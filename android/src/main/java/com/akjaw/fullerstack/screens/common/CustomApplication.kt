package com.akjaw.fullerstack.screens.common

import android.app.Application
import com.akjaw.fullerstack.composition.modules.androidModule
import com.akjaw.fullerstack.composition.modules.networkModule
import composition.common
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.androidXModule

class CustomApplication : Application(), DIAware {
    override val di by DI.lazy {
        import(androidXModule(this@CustomApplication))
        import(androidModule)
        import(networkModule)
        import(common)
    }

    override fun onCreate() {
        super.onCreate()

//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
}
