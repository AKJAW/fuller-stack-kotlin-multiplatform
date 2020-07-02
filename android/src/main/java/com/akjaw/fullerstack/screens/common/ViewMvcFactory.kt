package com.akjaw.fullerstack.screens.common

import android.view.LayoutInflater
import android.view.ViewGroup
import com.akjaw.fullerstack.screens.common.mainactivity.MainViewMvc
import com.akjaw.fullerstack.screens.editor.NoteEditorViewMvc
import com.akjaw.fullerstack.screens.editor.NoteEditorViewMvcImpl
import com.akjaw.fullerstack.screens.list.NotesListViewMvc
import com.akjaw.fullerstack.screens.list.NotesListViewMvcImpl
import com.akjaw.fullerstack.screens.list.recyclerview.NoteItemViewMvc
import com.akjaw.fullerstack.screens.list.recyclerview.NoteItemViewMvcImpl
import helpers.date.PatternProvider

class ViewMvcFactory(private val layoutInflater: LayoutInflater, private val patternProvider: PatternProvider) {

    fun getMainViewMvc(parent: ViewGroup?) = MainViewMvc(layoutInflater, parent)

    fun getNotesListViewMvc(parent: ViewGroup?): NotesListViewMvc =
        NotesListViewMvcImpl(layoutInflater, parent, this)

    fun getNoteItemViewMvc(parent: ViewGroup?): NoteItemViewMvc = NoteItemViewMvcImpl(
        layoutInflater,
        parent,
        patternProvider.getNotesListItemPattern()
    )

    fun getNoteEditorViewMvc(parent: ViewGroup?): NoteEditorViewMvc =
        NoteEditorViewMvcImpl(layoutInflater, parent)
}
