package com.akjaw.fullerstack.screens.list.recyclerview

import androidx.fragment.app.FragmentActivity
import model.NoteIdentifier
import model.NoteIdentifierMapper

class NotesSelectionTrackerFactory(
    private val activity: FragmentActivity,
    private val noteIdentifierMapper: NoteIdentifierMapper
) {
    fun create(
        initialSelectedNotes: List<NoteIdentifier>,
        onActionModeDestroyed: () -> Unit,
        onNoteChanged: (NoteIdentifier) -> Unit
    ): NotesSelectionTracker {
        return NotesSelectionTracker(
            initialSelectedNotes = initialSelectedNotes,
            fragmentManager = activity.supportFragmentManager,
            noteIdentifierMapper = noteIdentifierMapper,
            onActionModeDestroyed = onActionModeDestroyed,
            onNoteChanged = onNoteChanged
        ).apply {
            if(initialSelectedNotes.isNotEmpty()){
                activity.startActionMode(this)
            }
        }
    }
}
