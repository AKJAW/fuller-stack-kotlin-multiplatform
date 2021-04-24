package com.akjaw.fullerstack.screens.list.recyclerview

import com.akjaw.framework.utility.KeyboardCloser
import helpers.date.PatternProvider
import model.Note
import model.toCreationTimestamp

class NotesListAdapterFactory(
    private val patternProvider: PatternProvider,
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
            dateFormat = patternProvider.getNotesListItemPattern(),
            onItemClicked = onItemClicked,
        )
    }
}
