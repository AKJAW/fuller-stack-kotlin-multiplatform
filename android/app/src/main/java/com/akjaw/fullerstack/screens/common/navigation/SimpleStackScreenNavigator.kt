package com.akjaw.fullerstack.screens.common.navigation

import android.content.Context
import com.akjaw.fullerstack.screens.common.ParcelableNote
import com.akjaw.fullerstack.screens.common.navigation.keys.NoteEditorScreen
import com.zhuinden.simplestackextensions.navigatorktx.backstack

class SimpleStackScreenNavigator(
    private val context: Context
) : ScreenNavigator {

    override fun openAddNoteScreen() {
        context.backstack.goTo(NoteEditorScreen())
    }

    override fun goBack() {
        context.backstack.goBack()
    }

    override fun openEditNoteScreen(note: ParcelableNote) {
        context.backstack.goTo(NoteEditorScreen(note))
    }
}
