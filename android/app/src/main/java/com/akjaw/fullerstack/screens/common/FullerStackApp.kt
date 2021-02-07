package com.akjaw.fullerstack.screens.common

import android.app.Application
import android.content.Context
import android.content.res.Resources
import androidx.appcompat.app.AppCompatDelegate
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.authentication.composition.authenticationModule
import com.akjaw.fullerstack.authentication.navigation.AfterAuthenticationLauncher
import com.akjaw.fullerstack.composition.modules.androidModule
import com.akjaw.fullerstack.composition.modules.databaseModule
import com.akjaw.fullerstack.composition.modules.networkModule
import com.akjaw.fullerstack.composition.modules.socketModule
import com.akjaw.fullerstack.helpers.logger.HyperlinkedDebugTree
import com.akjaw.fullerstack.screens.common.navigation.MultiStack
import com.akjaw.fullerstack.screens.common.navigation.keys.NotesListScreen
import com.akjaw.fullerstack.screens.common.navigation.keys.ProfileScreen
import com.akjaw.fullerstack.screens.common.navigation.keys.SettingsScreen
import composition.common
import helpers.date.NotesDatePatternStorageKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import timber.log.Timber

class FullerStackApp : Application(), DIAware {
    private val applicationScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override val di by DI.lazy {
        import(androidXModule(this@FullerStackApp))
        bind<Context>("ApplicationContext") with singleton { this@FullerStackApp.applicationContext }
        bind<CoroutineScope>("ApplicationCoroutineScope") with singleton { this@FullerStackApp.applicationScope }
        bind<AfterAuthenticationLauncher>() with singleton { MainActivityAfterAuthenticationLauncher() }
        bind<MultiStack>() with singleton {
            val rootKeys = listOf(
                NotesListScreen(),
                ProfileScreen(),
                SettingsScreen()
            )
            MultiStack(rootKeys)
        }
        bind<NotesDatePatternStorageKey>() with singleton {
            NotesDatePatternStorageKey(resources.getString(R.string.note_date_pattern_key))
        }
        import(androidModule)
        import(databaseModule)
        import(networkModule)
        import(socketModule)
        import(authenticationModule)
        import(common)
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(HyperlinkedDebugTree())

//        LeakCanary.config = LeakCanary.config.copy(dumpHeap = false)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
}
