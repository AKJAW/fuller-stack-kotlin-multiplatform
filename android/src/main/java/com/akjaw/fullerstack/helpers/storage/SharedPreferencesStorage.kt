package com.akjaw.fullerstack.helpers.storage

import android.content.SharedPreferences
import helpers.storage.Storage

class SharedPreferencesStorage(private val sharedPreferences: SharedPreferences) : Storage {

    companion object {
        const val PREFERENCES_NAME = "Storage"
    }

    override fun getString(key: String): String? = sharedPreferences.getString(key, null)

    override fun setString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }
}
