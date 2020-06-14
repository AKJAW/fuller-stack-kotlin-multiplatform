package com.akjaw.fullerstack.screens.editor

import android.app.Dialog
import android.os.Bundle
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.screens.common.ViewMvcFactory
import com.akjaw.fullerstack.screens.common.base.BaseDialogFragment
import org.kodein.di.instance

class NoteEditorDialog : BaseDialogFragment(), NoteEditorViewMvc.Listener {

    companion object {
        fun newNoteEditorDialog(): NoteEditorDialog {
            return NoteEditorDialog()
        }
    }

    private val viewMvcFactory: ViewMvcFactory by instance<ViewMvcFactory>()
    private lateinit var viewMvc: NoteEditorViewMvc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewMvc = viewMvcFactory.getNoteEditorViewMvc(null)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext(), R.style.NotesEditorDialog)
        dialog.setContentView(viewMvc.rootView)

        viewMvc.showTitleError("Error title")

        return dialog
    }

    override fun onStart() {
        super.onStart()
        viewMvc.registerListener(this)
    }

    override fun onStop() {
        super.onStop()
        viewMvc.unregisterListener(this)
    }

    override fun onAddClicked() {
        TODO("Not yet implemented")
    }

    override fun onCancelClicked() {
        TODO("Not yet implemented")
    }
}