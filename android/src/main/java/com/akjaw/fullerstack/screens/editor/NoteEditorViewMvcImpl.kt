package com.akjaw.fullerstack.screens.editor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.MenuRes
import androidx.appcompat.widget.Toolbar
import com.akjaw.fullerstack.android.R
import com.google.android.material.textfield.TextInputLayout

class NoteEditorViewMvcImpl(inflater: LayoutInflater, parent: ViewGroup?, @MenuRes menu: Int) : NoteEditorViewMvc() {
    override val rootView: View = inflater.inflate(R.layout.layout_note_editor, parent, false)

    private val toolbar: Toolbar = findViewById(R.id.toolbar)
    private val titleLayout: TextInputLayout = findViewById(R.id.title_layout)
    private val titleEditText: EditText = findViewById(R.id.title_edit_text)
    private val bodyEditText: EditText = findViewById(R.id.body_edit_text)

    init {
        toolbar.inflateMenu(menu)
        toolbar.setNavigationIcon(R.drawable.ic_close_24dp)
        toolbar.setNavigationOnClickListener {
            listeners.onEach { listener ->
                listener.onCancelClicked()
            }
        }
        toolbar.setOnMenuItemClickListener { item ->
            listeners.onEach { listener ->
                listener.onActionClicked()
            }
            true
        }
    }

    override fun setToolbarTitle(text: String) {
        toolbar.title = text
    }

    override fun getNoteTitle(): String = titleEditText.text.toString()

    override fun showNoteTitleError(text: String) {
        titleLayout.error = text
    }

    override fun hideNoteTitleError() {
        titleLayout.error = ""
    }

    override fun getNoteContent(): String = bodyEditText.text.toString()

}
