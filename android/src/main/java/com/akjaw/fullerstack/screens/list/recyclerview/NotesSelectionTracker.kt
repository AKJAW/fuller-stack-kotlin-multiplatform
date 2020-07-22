package com.akjaw.fullerstack.screens.list.recyclerview

import androidx.fragment.app.FragmentManager
import com.akjaw.fullerstack.screens.list.DeleteNotesConfirmDialog
import com.akjaw.fullerstack.screens.list.recyclerview.selection.NotesListActionMode
import model.NoteIdentifier
import model.NoteIdentifierMapper

class NotesSelectionTracker(
    initialSelectedNotes: List<NoteIdentifier>,
    private val fragmentManager: FragmentManager, //TODO replace with an abstraction
    private val noteIdentifierMapper: NoteIdentifierMapper,
    private val notesListActionMode: NotesListActionMode,
    private val onNoteChanged: (NoteIdentifier) -> Unit
) {

    init {
        notesListActionMode.initialize(
            onDeleteClicked = ::openDeleteDialog,
            onActionModeDestroyed = ::clearSelection
        )
        if(initialSelectedNotes.isNotEmpty()) {
            notesListActionMode.startActionMode()
        }
    }

    val selectedNoteIdentifiers: MutableList<NoteIdentifier> = initialSelectedNotes.toMutableList()

    fun select(noteIdentifier: NoteIdentifier) {
        if(selectedNoteIdentifiers.contains(noteIdentifier)){
            selectedNoteIdentifiers.remove(noteIdentifier)
        } else {
            selectedNoteIdentifiers.add(noteIdentifier)
        }

        onNoteChanged(noteIdentifier)
        toggleActionMode()
    }

    private fun toggleActionMode() {
        if (selectedNoteIdentifiers.isNotEmpty()) {
            notesListActionMode.startActionMode()
        } else {
            notesListActionMode.exitActionMode()
        }
    }

    fun isSelected(noteIdentifier: NoteIdentifier) = selectedNoteIdentifiers.contains(noteIdentifier)

    fun getSelectedIds(): List<Int> {
        return noteIdentifierMapper.toInt(selectedNoteIdentifiers)
    }

    fun isSelectionModeEnabled() = selectedNoteIdentifiers.isNotEmpty()

    private fun openDeleteDialog() {
        val dialog = DeleteNotesConfirmDialog.newInstance(selectedNoteIdentifiers) {
            notesListActionMode.exitActionMode()
        }
        dialog.show(fragmentManager, "DeleteNotes")
    }

    private fun clearSelection() {
        selectedNoteIdentifiers.clear()
    }
}
