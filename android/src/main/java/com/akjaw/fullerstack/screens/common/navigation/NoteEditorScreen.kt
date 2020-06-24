package com.akjaw.fullerstack.screens.common.navigation

import androidx.fragment.app.Fragment
import com.akjaw.fullerstack.screens.editor.NoteEditorFragment
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey
import kotlinx.android.parcel.Parcelize

@Parcelize
class NoteEditorScreen: DefaultFragmentKey() {
    override fun instantiateFragment(): Fragment = NoteEditorFragment()
}