package com.akjaw.fullerstack.screens.editor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.akjaw.fullerstack.android.R
import com.google.android.material.textfield.TextInputLayout

class NoteEditorViewMvcImpl(inflater: LayoutInflater, parent: ViewGroup?) : NoteEditorViewMvc() {
    override val rootView: View = inflater.inflate(R.layout.layout_note_editor, parent, false)

    private val titleLayout: TextInputLayout = findViewById(R.id.title_layout)
    private val titleEditText: EditText = findViewById(R.id.title_edit_text)
    private val bodyEditText: EditText = findViewById(R.id.body_edit_text)

    override fun getTitle(): String = titleEditText.text.toString()

    override fun showTitleError(text: String) {
        titleLayout.error = text
    }

    override fun hideTitleError() {
        titleLayout.error = ""
    }

    override fun getBody(): String = bodyEditText.text.toString()

}