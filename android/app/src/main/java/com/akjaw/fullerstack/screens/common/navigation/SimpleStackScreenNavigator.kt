package com.akjaw.fullerstack.screens.common.navigation

import com.akjaw.fullerstack.screens.common.ParcelableNote
import com.akjaw.fullerstack.screens.common.navigation.keys.NoteEditorScreen

class SimpleStackScreenNavigator(
    private val multiStack: MultiStack
) : ScreenNavigator {

    override fun openAddNoteScreen() {
        multiStack.getSelectedStack().goTo(NoteEditorScreen())
    }

    override fun goBack() {
        multiStack.getSelectedStack().goBack()
    }

    override fun openEditNoteScreen(note: ParcelableNote) {
        multiStack.getSelectedStack().goTo(NoteEditorScreen(note))
    }
}
