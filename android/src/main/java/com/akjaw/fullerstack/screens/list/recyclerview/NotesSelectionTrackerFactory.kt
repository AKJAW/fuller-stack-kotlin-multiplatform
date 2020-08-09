package com.akjaw.fullerstack.screens.list.recyclerview

import androidx.fragment.app.FragmentActivity
import com.akjaw.fullerstack.screens.list.recyclerview.selection.NotesListActionMode
import model.CreationTimestamp

class NotesSelectionTrackerFactory(
    private val activity: FragmentActivity,
    private val notesListActionMode: NotesListActionMode
) {
    fun create(
        initialSelectedNotes: List<CreationTimestamp>,
        onNoteChanged: (CreationTimestamp) -> Unit
    ): NotesSelectionTracker {
        return NotesSelectionTracker(
            initialSelectedNotes = initialSelectedNotes,
            fragmentManager = activity.supportFragmentManager,
            notesListActionMode = notesListActionMode,
            onNoteChanged = onNoteChanged
        )
    }
}
