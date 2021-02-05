package com.akjaw.fullerstack.screens.list.recyclerview

import model.Note
import model.toCreationTimestamp

class NotesListAdapterFactory(
    private val notesSelectionTrackerFactory: NotesSelectionTrackerFactory,
) {
    fun create(
        initialSelectedNotes: List<Long>?,
        onItemClicked: (Note) -> Unit,
    ): NotesListAdapter {
        val selectedNotes = initialSelectedNotes?.map { it.toCreationTimestamp() } ?: emptyList()

        return NotesListAdapter(
            initialSelectedNotes = selectedNotes,
            notesSelectionTrackerFactory = notesSelectionTrackerFactory,
            onItemClicked = onItemClicked,
        )
    }
}
