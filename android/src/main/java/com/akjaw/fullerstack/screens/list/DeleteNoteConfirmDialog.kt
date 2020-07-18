package com.akjaw.fullerstack.screens.list

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.helpers.viewmodel.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di

class DeleteNoteConfirmDialog : DialogFragment(), DIAware {

    override val di: DI by di()

    private val viewModel: NotesListViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = activity ?: throw IllegalStateException("Activity is null")

        val builder = MaterialAlertDialogBuilder(activity, R.style.AppTheme_AlertDialogTheme)
        builder
            .setMessage("Are you sure you want to delete TODO plural notes")
            .setPositiveButton("Yes") { dialog: DialogInterface?, id: Int ->

            }
            .setNegativeButton("Cancel") { dialog: DialogInterface?, id: Int ->

            }

        return builder.create()
    }

}
