package com.akjaw.fullerstack.screens.common

import android.app.Application
import android.content.Context
import com.akjaw.fullerstack.composition.modules.androidModule
import com.akjaw.fullerstack.composition.modules.databaseModule
import com.akjaw.fullerstack.composition.modules.networkModule
import com.akjaw.fullerstack.helpers.logger.HyperlinkedDebugTree
import composition.common
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.bind
import org.kodein.di.singleton
import timber.log.Timber

class CustomApplication : Application(), DIAware {
    private val applicationScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override val di by DI.lazy {
        import(androidXModule(this@CustomApplication))
        bind<Context>("ApplicationContext") with singleton { this@CustomApplication.applicationContext }
        bind<CoroutineScope>("ApplicationCoroutineScope") with singleton { this@CustomApplication.applicationScope }
        import(androidModule)
        import(databaseModule)
        import(networkModule)
        import(common)
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(HyperlinkedDebugTree())

//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }


}
