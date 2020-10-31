package com.akjaw.fullerstack.composition.modules

import androidx.fragment.app.FragmentActivity
import com.akjaw.fullerstack.screens.common.main.BottomNavigationHelper
import com.akjaw.fullerstack.screens.common.navigation.DialogManager
import com.akjaw.fullerstack.screens.common.navigation.FragmentDialogManager
import com.akjaw.fullerstack.screens.common.navigation.MultiStack
import com.akjaw.fullerstack.screens.common.navigation.ScreenNavigator
import com.akjaw.fullerstack.screens.common.navigation.SimpleStackScreenNavigator
import com.akjaw.fullerstack.screens.common.navigation.keys.NotesListScreen
import com.akjaw.fullerstack.screens.common.navigation.keys.ProfileScreen
import com.akjaw.fullerstack.screens.common.navigation.keys.SettingsScreen
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val navigatorModule = DI.Module("navigatorModule") {
    bind<MultiStack>() with singleton {
        val rootKeys = listOf(
            NotesListScreen(),
            ProfileScreen(),
            SettingsScreen()
        )
        MultiStack(rootKeys)
    }
    bind<ScreenNavigator>() with singleton { SimpleStackScreenNavigator(instance()) }
    bind<DialogManager>() with singleton {
        val fragmentActivity = instance<FragmentActivity>()
        FragmentDialogManager(fragmentActivity.supportFragmentManager)
    }
    bind<BottomNavigationHelper>() with singleton { BottomNavigationHelper(instance()) }
}
