package com.akjaw.fullerstack.screens.common.navigation

import android.content.Context

interface ScreenNavigator {

    fun openAddNoteScreen(context: Context)

    fun goBack(context: Context)

}
