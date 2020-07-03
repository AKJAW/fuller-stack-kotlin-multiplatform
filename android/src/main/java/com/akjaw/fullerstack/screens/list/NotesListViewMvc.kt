package com.akjaw.fullerstack.screens.list

import com.akjaw.fullerstack.screens.common.base.BaseObservableViewMvc
import model.Note

abstract class NotesListViewMvc : BaseObservableViewMvc<NotesListViewMvc.Listener>() {

    interface Listener {

        fun onNoteClicked(note: Note)

        fun onAddNoteClicked()
    }

    abstract fun setNotes(notes: List<Note>)

    abstract fun showLoading()

    abstract fun hideLoading()

    abstract fun showError()
}
