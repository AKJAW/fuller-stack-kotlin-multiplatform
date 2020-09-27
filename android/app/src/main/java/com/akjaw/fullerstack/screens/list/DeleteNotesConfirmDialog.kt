package com.akjaw.fullerstack.screens.list

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.akjaw.fullerstack.android.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import model.CreationTimestamp
import model.toCreationTimestamp
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.direct
import org.kodein.di.instance
import java.io.Serializable

class DeleteNotesConfirmDialog : DialogFragment(), DIAware {

    companion object {
        private const val EXTRA_NOTES = "EXTRA_NOTES"
        private const val ON_NOTES_DELETED = "ON_NOTES_DELETED"

        fun newInstance(
            noteIdentifiers: List<CreationTimestamp>,
            onNotesDeleted: () -> Unit
        ): DeleteNotesConfirmDialog {
            return DeleteNotesConfirmDialog().apply {
                val args = Bundle()
                val creationUnixTimestamps = noteIdentifiers.map { it.unix }
                args.putLongArray(EXTRA_NOTES, creationUnixTimestamps.toLongArray())
                args.putSerializable(ON_NOTES_DELETED, onNotesDeleted as Serializable)
                arguments = args
            }
        }
    }

    private lateinit var onNotesDeleted: () -> Unit
    private lateinit var creationUnixTimestamps: List<Long>
    override val di: DI by di()
    private val viewModel: NotesListViewModel by activityViewModels {
        di.direct.instance()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable("ON_NOTES_DELETED", onNotesDeleted as Serializable)
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("UNCHECKED_CAST")
        onNotesDeleted = arguments?.getSerializable(ON_NOTES_DELETED) as? () -> Unit ?: return dismiss()
        creationUnixTimestamps = arguments?.getLongArray(EXTRA_NOTES)?.toList() ?: return dismiss()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = activity ?: throw IllegalStateException("Activity is null")

        val builder = MaterialAlertDialogBuilder(activity, R.style.AppTheme_AlertDialogTheme)
        val message = resources.getQuantityString(
            R.plurals.delete_notes_dialog_message,
            creationUnixTimestamps.count(),
            creationUnixTimestamps.count()
        )
        builder
            .setMessage(message)
            .setPositiveButton(getString(R.string.delete_notes_dialog_confirm)) { dialog: DialogInterface?, id: Int ->
                val creationTimestamps = creationUnixTimestamps.map { it.toCreationTimestamp() }
                viewModel.deleteNotes(creationTimestamps)
                onNotesDeleted.invoke()
            }
            .setNegativeButton(getString(R.string.delete_notes_dialog_cancel)) { dialog: DialogInterface?, id: Int ->
                dialog?.dismiss()
            }

        return builder.create()
    }
}
