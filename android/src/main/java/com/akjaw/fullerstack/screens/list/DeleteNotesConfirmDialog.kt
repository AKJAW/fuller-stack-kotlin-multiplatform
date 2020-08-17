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

class DeleteNotesConfirmDialog : DialogFragment(), DIAware {

    companion object {
        private const val EXTRA_NOTES = "EXTRA_NOTES"

        fun newInstance(
            noteIdentifiers: List<CreationTimestamp>,
            onNotesDeleted: () -> Unit
        ): DeleteNotesConfirmDialog {
            return DeleteNotesConfirmDialog().apply {
                val args = Bundle()
                val creationUnixTimestamps = noteIdentifiers.map { it.unix }
                args.putLongArray(EXTRA_NOTES, creationUnixTimestamps.toLongArray())
                arguments = args
                this.onNotesDeleted = onNotesDeleted
            }
        }
    }

    private lateinit var onNotesDeleted: () -> Unit // TODO should this be nulled out / is this kept on screen rotation
    private lateinit var creationUnixTimestamps: List<Long>
    override val di: DI by di()

    private val viewModel: NotesListViewModel by activityViewModels {
        di.direct.instance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        creationUnixTimestamps = arguments?.getLongArray(EXTRA_NOTES)?.toList() ?: return dismiss()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = activity ?: throw IllegalStateException("Activity is null")

        val builder = MaterialAlertDialogBuilder(activity, R.style.AppTheme_AlertDialogTheme)
        builder
            .setMessage("Are you sure you want to delete TODO plural notes")
            .setPositiveButton("Yes") { dialog: DialogInterface?, id: Int ->
                val creationTimestamps = creationUnixTimestamps.map { it.toCreationTimestamp() }
                viewModel.deleteNotes(creationTimestamps)
                onNotesDeleted.invoke()
            }
            .setNegativeButton("Cancel") { dialog: DialogInterface?, id: Int ->
                dialog?.dismiss()
            }

        return builder.create()
    }
}
