package com.akjaw.fullerstack.screens.noteslist

import com.akjaw.fullerstack.screens.common.base.BaseObservableViewMvc
import data.Note

abstract class NotesListViewMvc : BaseObservableViewMvc<NotesListViewMvc.Listener>() {

    interface Listener {

        fun onNoteClicked(title: String)

        fun onAddNoteClicked()
    }

    abstract fun setNotes(notes: List<Note>)

    abstract fun showLoading()

    abstract fun hideLoading()

}
