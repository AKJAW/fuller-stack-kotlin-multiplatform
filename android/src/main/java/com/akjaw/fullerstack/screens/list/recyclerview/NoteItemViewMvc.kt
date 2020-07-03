package com.akjaw.fullerstack.screens.list.recyclerview

import com.akjaw.fullerstack.screens.common.base.BaseObservableViewMvc
import model.Note

abstract class NoteItemViewMvc : BaseObservableViewMvc<NoteItemViewMvc.Listener>() {

    interface Listener {
        fun onNoteClicked(note: Note)
    }

    abstract fun setNote(note: Note)
}
