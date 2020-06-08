package com.akjaw.fullerstack.screens.common

import android.view.LayoutInflater
import android.view.ViewGroup
import com.akjaw.fullerstack.screens.common.mainactivity.MainViewMvc
import com.akjaw.fullerstack.screens.noteslist.NotesListViewMvc
import com.akjaw.fullerstack.screens.noteslist.recyclerview.NoteItemViewMvc
import helpers.date.PatternProvider

class ViewMvcFactory(private val layoutInflater: LayoutInflater, private val patternProvider: PatternProvider) {
    fun getMainViewMvc(parent: ViewGroup?) = MainViewMvc(layoutInflater, parent)
    fun getNotesListViewMvc(parent: ViewGroup?) = NotesListViewMvc(layoutInflater, parent, this, patternProvider)
    fun getNoteItemViewMvc(parent: ViewGroup?) = NoteItemViewMvc(layoutInflater, parent)
}
