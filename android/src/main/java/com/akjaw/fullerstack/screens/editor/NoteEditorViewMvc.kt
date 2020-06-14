package com.akjaw.fullerstack.screens.editor

import com.akjaw.fullerstack.screens.common.base.BaseObservableViewMvc

abstract class NoteEditorViewMvc : BaseObservableViewMvc<NoteEditorViewMvc.Listener>() {

    interface Listener {
        fun onAddClicked()
        fun onCancelClicked()
    }

    abstract fun getTitle(): String

    abstract fun showTitleError(text: String)

    abstract fun hideTitleError()

    abstract fun getBody(): String
}
