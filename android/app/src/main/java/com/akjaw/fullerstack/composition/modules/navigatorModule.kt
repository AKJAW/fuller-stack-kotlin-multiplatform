package com.akjaw.fullerstack.composition.modules

import androidx.fragment.app.FragmentActivity
import com.akjaw.fullerstack.screens.common.navigation.DialogManager
import com.akjaw.fullerstack.screens.common.navigation.FragmentDialogManager
import com.akjaw.fullerstack.screens.common.navigation.ScreenNavigator
import com.akjaw.fullerstack.screens.common.navigation.SimpleStackScreenNavigator
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val navigatorModule = DI.Module("navigatorModule") {
    bind<ScreenNavigator>() with singleton { SimpleStackScreenNavigator(instance()) }
    bind<DialogManager>() with singleton {
        val fragmentActivity = instance<FragmentActivity>("FragmentActivity")
        FragmentDialogManager(fragmentActivity.supportFragmentManager)
    }
}
