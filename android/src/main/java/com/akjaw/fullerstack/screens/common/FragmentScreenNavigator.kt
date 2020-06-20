package com.akjaw.fullerstack.screens.common

import androidx.fragment.app.FragmentManager
import com.akjaw.fullerstack.screens.editor.NoteEditorDialog

class FragmentScreenNavigator(
    private val fragmentManager: FragmentManager
) : ScreenNavigator {

    override fun openAddNoteScreen() {
        val dialog = NoteEditorDialog.newNoteEditorDialog()
        dialog.show(fragmentManager, null)//TODO should there be a tag?
    }

}