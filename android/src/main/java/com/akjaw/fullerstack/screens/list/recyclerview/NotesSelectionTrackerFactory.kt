package com.akjaw.fullerstack.screens.list.recyclerview

import com.akjaw.fullerstack.screens.common.navigation.DialogManager
import com.akjaw.fullerstack.screens.list.recyclerview.selection.NotesListActionMode
import model.CreationTimestamp

class NotesSelectionTrackerFactory(
    private val dialogManager: DialogManager,
    private val notesListActionMode: NotesListActionMode
) {
    fun create(
        initialSelectedNotes: List<CreationTimestamp>,
        onNoteChanged: (List<CreationTimestamp>) -> Unit
    ): NotesSelectionTracker {
        return NotesSelectionTracker(
            initialSelectedNotes = initialSelectedNotes,
            dialogManager = dialogManager,
            notesListActionMode = notesListActionMode,
            onNoteChanged = onNoteChanged
        )
    }
}
