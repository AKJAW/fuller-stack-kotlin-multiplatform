package com.akjaw.fullerstack.screens.list

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.akjaw.fullerstack.android.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.direct
import org.kodein.di.instance

class DeleteNotesConfirmDialog : DialogFragment(), DIAware {

    companion object {
        private const val EXTRA_NOTES = "EXTRA_NOTES"

        fun newInstance(noteIds: List<Int>): DeleteNotesConfirmDialog {
            return DeleteNotesConfirmDialog().apply {
                val args = Bundle()
                args.putIntegerArrayList(EXTRA_NOTES, ArrayList(noteIds))
                arguments = args
            }
        }
    }

    interface DeleteNotesConfirmationListener {
        fun onNotesDeleted()
    }

    private var listener: DeleteNotesConfirmationListener? = null
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
                viewModel.deleteNotes(noteIds)
                listener?.onNotesDeleted()
            }
            .setNegativeButton("Cancel") { dialog: DialogInterface?, id: Int ->
                dialog?.dismiss()
            }

        return builder.create()
    }

    fun setPositiveClickListener(listener: DeleteNotesConfirmationListener) {
        this.listener = listener
    }

    override fun onDestroy() {
        super.onDestroy()
        listener = null
    }
}
