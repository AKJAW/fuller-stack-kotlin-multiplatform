package com.akjaw.fullerstack.screens.common

import android.app.Application
import com.akjaw.fullerstack.composition.modules.androidModule
import com.akjaw.fullerstack.composition.modules.networkModule
import com.akjaw.fullerstack.helpers.logger.HyperlinkedDebugTree
import composition.common
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.androidXModule
import timber.log.Timber

class CustomApplication : Application(), DIAware {
    override val di by DI.lazy {
        import(androidXModule(this@CustomApplication))
        import(androidModule)
        import(networkModule)
        import(common)
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(HyperlinkedDebugTree())

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
}
