package com.akjaw.fullerstack.screens.list.recyclerview

import helpers.date.PatternProvider
import model.CreationTimestamp
import model.Note

class NotesListAdapterFactory(
    private val patternProvider: PatternProvider,
    private val notesSelectionTrackerFactory: NotesSelectionTrackerFactory
) {
    fun create(
        initialSelectedNotes: List<Long>?,
        onItemClicked: (Note) -> Unit
    ): NotesListAdapter {
        val selectedNotes = initialSelectedNotes?.map { CreationTimestamp(it) } ?: emptyList()

        return NotesListAdapter(
            initialSelectedNotes = selectedNotes,
            notesSelectionTrackerFactory = notesSelectionTrackerFactory,
            dateFormat = patternProvider.getNotesListItemPattern(),
            onItemClicked = onItemClicked
        )
    }
}
