package com.akjaw.fullerstack.screens.common.navigation.keys

import com.akjaw.fullerstack.screens.common.ParcelableNote
import com.akjaw.fullerstack.screens.common.base.BaseFragment
import com.akjaw.fullerstack.screens.editor.NoteEditorFragment
import kotlinx.android.parcel.Parcelize

@Parcelize
class NoteEditorScreen(private val note: ParcelableNote? = null) : MultiStackFragmentKey() {

    override fun getKeyIdentifier(): String = RootFragments.HOME.name

    override fun instantiateFragment(): BaseFragment = NoteEditorFragment.newInstance(note)
}
