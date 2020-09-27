package com.akjaw.fullerstack.screens.common.navigation

import androidx.fragment.app.FragmentManager
import com.akjaw.fullerstack.screens.list.DeleteNotesConfirmDialog
import model.CreationTimestamp

class FragmentDialogManager(
    private val fragmentManager: FragmentManager
) : DialogManager {

    override fun showDeleteNotesConfirmDialog(noteIdentifiers: List<CreationTimestamp>, onNotesDeleted: () -> Unit) {
        val dialog = DeleteNotesConfirmDialog.newInstance(noteIdentifiers, onNotesDeleted)
        dialog.show(fragmentManager, "DeleteNotes")
    }
}
