package com.akjaw.fullerstack.screens.common.navigation

import android.content.Context
import com.akjaw.fullerstack.screens.common.ParcelableNote

interface ScreenNavigator {

    fun openAddNoteScreen(context: Context)

    fun goBack(context: Context)

    fun openEditNoteScreen(context: Context, note: ParcelableNote)
}
