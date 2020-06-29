package com.akjaw.fullerstack.screens.editor

import com.akjaw.fullerstack.screens.common.base.BaseObservableViewMvc

abstract class NoteEditorViewMvc : BaseObservableViewMvc<NoteEditorViewMvc.Listener>() {

    interface Listener {
        fun onActionClicked()
        fun onCancelClicked()
    }

    abstract fun setAddToolbarTitle()

    abstract fun setUpdateToolbarTitle()

    abstract fun setNoteTitle(title: String)

    abstract fun getNoteTitle(): String

    abstract fun showNoteTitleError(text: String)

    abstract fun hideNoteTitleError()

    abstract fun setNoteContent(content: String)

    abstract fun getNoteContent(): String

}
