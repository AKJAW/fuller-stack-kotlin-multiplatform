package com.akjaw.fullerstack.screens.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.MenuRes
import com.akjaw.fullerstack.screens.common.mainactivity.MainViewMvc
import com.akjaw.fullerstack.screens.editor.NoteEditorViewMvc
import com.akjaw.fullerstack.screens.editor.NoteEditorViewMvcImpl
import com.akjaw.fullerstack.screens.noteslist.NotesListViewMvc
import com.akjaw.fullerstack.screens.noteslist.NotesListViewMvcImpl
import com.akjaw.fullerstack.screens.noteslist.recyclerview.NoteItemViewMvc
import com.akjaw.fullerstack.screens.noteslist.recyclerview.NoteItemViewMvcImpl
import helpers.date.PatternProvider

class ViewMvcFactory(private val layoutInflater: LayoutInflater, private val patternProvider: PatternProvider) {

    fun getMainViewMvc(parent: ViewGroup?) = MainViewMvc(layoutInflater, parent)

    fun getNotesListViewMvc(parent: ViewGroup?) : NotesListViewMvc
            = NotesListViewMvcImpl(layoutInflater, parent, this, patternProvider)

    fun getNoteItemViewMvc(parent: ViewGroup?): NoteItemViewMvc = NoteItemViewMvcImpl(layoutInflater, parent)

    fun getNoteEditorViewMvc(parent: ViewGroup?, @MenuRes menu: Int) : NoteEditorViewMvc
            = NoteEditorViewMvcImpl(layoutInflater, parent, menu)
}
