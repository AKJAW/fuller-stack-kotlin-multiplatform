package com.akjaw.fullerstack.screens.common.navigation.keys

import androidx.fragment.app.Fragment
import com.akjaw.fullerstack.screens.list.NotesListFragment
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey
import kotlinx.android.parcel.Parcelize

@Parcelize
class NotesListScreen: DefaultFragmentKey() {
    override fun instantiateFragment(): Fragment = NotesListFragment()
}
