package com.akjaw.fullerstack.dependencyinjection.modules

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.akjaw.fullerstack.helpers.storage.SharedPreferencesStorage
import com.akjaw.fullerstack.helpers.storage.SharedPreferencesStorage.Companion.PREFERENCES_NAME
import helpers.storage.Storage
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val androidModule = DI.Module("androidModule") {
    bind<Storage>() with singleton {
        val sharedPrefs = instance<Context>().getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
        SharedPreferencesStorage(sharedPrefs)
    }
}
