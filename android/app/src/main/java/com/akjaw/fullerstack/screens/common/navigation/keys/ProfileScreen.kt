package com.akjaw.fullerstack.screens.common.navigation.keys

import androidx.fragment.app.Fragment
import com.akjaw.fullerstack.screens.profile.ProfileFragment
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey
import kotlinx.android.parcel.Parcelize

@Parcelize
class ProfileScreen : DefaultFragmentKey() {
    override fun instantiateFragment(): Fragment = ProfileFragment()
}
