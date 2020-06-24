package com.akjaw.fullerstack.screens.list.recyclerview

import com.akjaw.fullerstack.screens.common.base.BaseObservableViewMvc

abstract class NoteItemViewMvc : BaseObservableViewMvc<NoteItemViewMvc.Listener>() {

    interface Listener {
        fun onNoteClicked(title: String)
    }

    abstract fun setTitle(titleText: String)

    abstract fun setDate(dateText: String)

}
