package com.akjaw.fullerstack.screens.common.navigation.keys

import androidx.fragment.app.Fragment
import com.akjaw.fullerstack.screens.settings.SettingsFragment
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey
import kotlinx.android.parcel.Parcelize

@Parcelize
class SettingsScreen : DefaultFragmentKey() {
    override fun instantiateFragment(): Fragment = SettingsFragment()
}
