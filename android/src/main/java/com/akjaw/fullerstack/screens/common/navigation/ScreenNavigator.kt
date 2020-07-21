package com.akjaw.fullerstack.screens.common.navigation

import com.akjaw.fullerstack.screens.common.ParcelableNote

interface ScreenNavigator {

    fun openAddNoteScreen()

    fun goBack()

    fun openEditNoteScreen(note: ParcelableNote)
}
