package com.akjaw.fullerstack.screens.common.navigation

import android.content.Context
import com.akjaw.fullerstack.screens.common.navigation.keys.NoteEditorScreen
import com.zhuinden.simplestackextensions.navigatorktx.backstack
import data.Note

class SimpleStackScreenNavigator : ScreenNavigator {

    override fun openAddNoteScreen(context: Context) {
        context.backstack.goTo(NoteEditorScreen())
    }

    override fun goBack(context: Context) {
        context.backstack.goBack()
    }

    override fun openEditNoteScreen(context: Context, note: Note) {
        TODO("Not yet implemented")
    }

}
