package com.akjaw.fullerstack.dependencyinjection.modules

import androidx.fragment.app.FragmentManager
import com.akjaw.fullerstack.screens.common.FragmentScreenNavigator
import com.akjaw.fullerstack.screens.common.ScreenNavigator
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val navigatorModule: (FragmentManager) -> DI.Module = { fragmentManager ->
    DI.Module("navigatorModule") {
        bind<ScreenNavigator>() with singleton {
            FragmentScreenNavigator(fragmentManager)
        }
    }
}
