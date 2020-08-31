package com.akjaw.fullerstack.screens.list.recyclerview

import androidx.fragment.app.FragmentManager
import com.akjaw.fullerstack.screens.list.DeleteNotesConfirmDialog
import com.akjaw.fullerstack.screens.list.recyclerview.selection.NotesListActionMode
import model.CreationTimestamp

class NotesSelectionTracker(
    initialSelectedNotes: List<CreationTimestamp>,
    private val fragmentManager: FragmentManager, // TODO replace with an abstraction
    private val notesListActionMode: NotesListActionMode,
    private val onNoteChanged: (List<CreationTimestamp>) -> Unit
) {

    init {
        notesListActionMode.initialize(
            onDeleteClicked = ::openDeleteDialog,
            onActionModeDestroyed = ::clearSelection
        )
        if (initialSelectedNotes.isNotEmpty()) {
            notesListActionMode.startActionMode()
        }
    }

    private val selectedCreationTimestamp: MutableList<CreationTimestamp> = initialSelectedNotes.toMutableList()

    fun select(creationTimestamp: CreationTimestamp) {
        if (selectedCreationTimestamp.contains(creationTimestamp)) {
            selectedCreationTimestamp.remove(creationTimestamp)
        } else {
            selectedCreationTimestamp.add(creationTimestamp)
        }

        onNoteChanged(listOf(creationTimestamp))
        toggleActionMode()
    }

    private fun toggleActionMode() {
        if (selectedCreationTimestamp.isNotEmpty()) {
            notesListActionMode.startActionMode()
            val numberOfSelectedNotes = selectedCreationTimestamp.count()
            notesListActionMode.setTitle(numberOfSelectedNotes.toString())
        } else {
            notesListActionMode.exitActionMode()
        }
    }

    fun isSelected(creationTimestamp: CreationTimestamp) = selectedCreationTimestamp.contains(creationTimestamp)

    fun getSelectedNotes(): List<Long> {
        return selectedCreationTimestamp.map { it.unix }
    }

    fun isSelectionModeEnabled() = selectedCreationTimestamp.isNotEmpty()

    private fun openDeleteDialog() {
        val dialog = DeleteNotesConfirmDialog.newInstance(selectedCreationTimestamp) {
            notesListActionMode.exitActionMode()
        }
        dialog.show(fragmentManager, "DeleteNotes")
    }

    private fun clearSelection() {
        val selectedNotes = selectedCreationTimestamp.toList()
        selectedCreationTimestamp.clear()
        onNoteChanged(selectedNotes)
    }
}
