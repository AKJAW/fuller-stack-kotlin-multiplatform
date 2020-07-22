package com.akjaw.fullerstack.screens.list

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.akjaw.fullerstack.android.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import model.NoteIdentifier
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.direct
import org.kodein.di.instance

class DeleteNotesConfirmDialog : DialogFragment(), DIAware {

    companion object {
        private const val EXTRA_NOTES = "EXTRA_NOTES"

        fun newInstance(
            noteIdentifiers: List<NoteIdentifier>,
            onNotesDeleted: () -> Unit
        ): DeleteNotesConfirmDialog {
            return DeleteNotesConfirmDialog().apply {
                val args = Bundle()
                val intIds = noteIdentifiers.map { it.id }
                args.putIntegerArrayList(EXTRA_NOTES, ArrayList(intIds))
                arguments = args
                this.onNotesDeleted = onNotesDeleted
            }
        }
    }

    private lateinit var onNotesDeleted: () -> Unit //TODO should this be nulled out
    private lateinit var noteIds: List<Int>
    override val di: DI by di()

    private val viewModel: NotesListViewModel by activityViewModels {
        di.direct.instance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        noteIds = arguments?.getIntegerArrayList(EXTRA_NOTES) ?: return dismiss()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = activity ?: throw IllegalStateException("Activity is null")

        val builder = MaterialAlertDialogBuilder(activity, R.style.AppTheme_AlertDialogTheme)
        builder
            .setMessage("Are you sure you want to delete TODO plural notes")
            .setPositiveButton("Yes") { dialog: DialogInterface?, id: Int ->
                val noteIdentifiers = noteIds.map { NoteIdentifier(it) }
                viewModel.deleteNotes(noteIdentifiers)
                onNotesDeleted.invoke()
            }
            .setNegativeButton("Cancel") { dialog: DialogInterface?, id: Int ->
                dialog?.dismiss()
            }

        return builder.create()
    }
}
