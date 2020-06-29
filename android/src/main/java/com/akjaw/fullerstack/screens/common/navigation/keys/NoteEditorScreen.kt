package com.akjaw.fullerstack.screens.common.navigation.keys

import androidx.fragment.app.Fragment
import com.akjaw.fullerstack.screens.common.ParcelableNote
import com.akjaw.fullerstack.screens.editor.NoteEditorFragment
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey
import kotlinx.android.parcel.Parcelize

@Parcelize
class NoteEditorScreen(private val note: ParcelableNote? = null): DefaultFragmentKey() {
    override fun instantiateFragment(): Fragment = NoteEditorFragment.newInstance(note)
}
