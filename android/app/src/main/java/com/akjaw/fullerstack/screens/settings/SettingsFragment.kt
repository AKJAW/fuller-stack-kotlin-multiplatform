package com.akjaw.fullerstack.screens.settings

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.helpers.storage.SharedPreferencesStorage
import com.soywiz.klock.DateFormat
import helpers.date.NoteDateFormat
import helpers.date.NotesDatePatternStorageKey
import helpers.date.PatternProvider
import helpers.date.PatternSaver
import helpers.date.toNoteDateFormat
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

class SettingsFragment : PreferenceFragmentCompat(), DIAware {
    override val di: DI by di()

    private val notesDatePatternStorageKey: NotesDatePatternStorageKey by instance()
    private val patternProvider: PatternProvider by instance()
    private val patternSaver: PatternSaver by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferenceManager.sharedPreferencesName = SharedPreferencesStorage.PREFERENCES_NAME
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_screen, rootKey)

        setUpDateFormatPreferences()
    }

    private fun setUpDateFormatPreferences() {
        findPreference<ListPreference>(notesDatePatternStorageKey.value)?.apply {
            val values = NoteDateFormat.values().map { it.value }.toTypedArray()
            entries = values
            entryValues = values

            val savedPattern = patternProvider.getPattern()
            if (value !=  savedPattern.toString()) {
                val noteDateFormat = savedPattern.toNoteDateFormat()
                val index = NoteDateFormat.values().indexOf(noteDateFormat)
                setValueIndex(index)
            }

            setOnPreferenceChangeListener { preference, newValue ->
                patternSaver.setPattern(DateFormat(newValue as String))
                true
            }
        }
    }
}
