package com.akjaw.fullerstack.screens.common.navigation.keys

import androidx.fragment.app.Fragment
import com.akjaw.fullerstack.screens.settings.SettingsFragment
import kotlinx.android.parcel.Parcelize

@Parcelize
class SettingsScreen : MultiStackFragmentKey() {

    override fun getKeyIdentifier(): String = RootFragments.SETTINGS.name

    override fun instantiateFragment(): Fragment = SettingsFragment()
}
