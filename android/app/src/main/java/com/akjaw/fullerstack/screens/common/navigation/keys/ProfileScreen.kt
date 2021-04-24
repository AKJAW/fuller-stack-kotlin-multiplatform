package com.akjaw.fullerstack.screens.common.navigation.keys

import com.akjaw.fullerstack.screens.common.base.BaseFragment
import com.akjaw.fullerstack.screens.profile.ProfileFragment
import kotlinx.android.parcel.Parcelize

@Parcelize
class ProfileScreen : MultiStackFragmentKey() {

    override fun getKeyIdentifier(): String = RootFragments.PROFILE.name

    override fun instantiateFragment(): BaseFragment = ProfileFragment()
}
