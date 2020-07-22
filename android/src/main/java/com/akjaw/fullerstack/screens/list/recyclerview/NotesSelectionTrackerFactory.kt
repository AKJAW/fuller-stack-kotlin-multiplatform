package com.akjaw.fullerstack.screens.list.recyclerview

import androidx.fragment.app.FragmentActivity
import com.akjaw.fullerstack.screens.list.recyclerview.selection.NotesListActionMode
import model.NoteIdentifier
import model.NoteIdentifierMapper

class NotesSelectionTrackerFactory(
    private val activity: FragmentActivity,
    private val noteIdentifierMapper: NoteIdentifierMapper,
    private val notesListActionMode: NotesListActionMode
) {
    fun create(
        initialSelectedNotes: List<NoteIdentifier>,
        onNoteChanged: (NoteIdentifier) -> Unit
    ): NotesSelectionTracker {
        return NotesSelectionTracker(
            initialSelectedNotes = initialSelectedNotes,
            fragmentManager = activity.supportFragmentManager,
            noteIdentifierMapper = noteIdentifierMapper,
            notesListActionMode = notesListActionMode,
            onNoteChanged = onNoteChanged
        )
    }
}
