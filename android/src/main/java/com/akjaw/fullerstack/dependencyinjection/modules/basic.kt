package com.akjaw.fullerstack.dependencyinjection.modules

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.akjaw.fullerstack.helpers.storage.SharedPreferencesStorage
import com.akjaw.fullerstack.helpers.storage.SharedPreferencesStorage.Companion.PREFERENCES_NAME
import com.akjaw.fullerstack.screens.common.ViewMvcFactory
import helpers.storage.Storage
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val basic = DI.Module("basic") {
    bind<Storage>() with singleton {
        val sharedPrefs = instance<Context>().getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
        SharedPreferencesStorage(sharedPrefs)
    }
    bind() from singleton { ViewMvcFactory(instance(), instance()) }
}
