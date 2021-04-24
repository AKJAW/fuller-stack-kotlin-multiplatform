package com.akjaw.fullerstack.screens.common.navigation.keys

import com.akjaw.fullerstack.screens.common.base.BaseFragment
import com.akjaw.fullerstack.screens.list.NotesListFragment
import kotlinx.android.parcel.Parcelize

@Parcelize
class NotesListScreen : MultiStackFragmentKey() {

    override fun getKeyIdentifier(): String = RootFragments.HOME.name

    override fun instantiateFragment(): BaseFragment = NotesListFragment()
}
