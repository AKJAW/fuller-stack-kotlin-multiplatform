package com.akjaw.fullerstack.screens.common

import android.view.LayoutInflater
import android.view.ViewGroup
import com.akjaw.fullerstack.screens.common.mainactivity.MainViewMvc
import com.akjaw.fullerstack.screens.noteslist.NotesListViewMvc

class ViewMvcFactory(private val layoutInflater: LayoutInflater) {
    fun getMainViewMvc(parent: ViewGroup?) = MainViewMvc(layoutInflater, parent)
    fun getNotesListViewMvc(parent: ViewGroup?) = NotesListViewMvc(layoutInflater, parent)
}
