package com.akjaw.fullerstack.screens.list.recyclerview

import com.akjaw.framework.utility.KeyboardCloser
import helpers.date.PatternProvider
import model.Note
import model.toCreationTimestamp

class NotesListAdapterFactory(
    private val patternProvider: PatternProvider,
    private val notesSelectionTrackerFactory: NotesSelectionTrackerFactory,
    private val keyboardCloser: KeyboardCloser
) {
    fun create(
        initialSelectedNotes: List<Long>?,
        onItemClicked: (Note) -> Unit,
        onSearchInputChange: (String) -> Unit
    ): NotesListAdapter {
        val selectedNotes = initialSelectedNotes?.map { it.toCreationTimestamp() } ?: emptyList()

        return NotesListAdapter(
            initialSelectedNotes = selectedNotes,
            notesSelectionTrackerFactory = notesSelectionTrackerFactory,
            dateFormat = patternProvider.getNotesListItemPattern(),
            keyboardCloser = keyboardCloser,
            onItemClicked = onItemClicked,
            onSearchInputChange = onSearchInputChange
        )
    }
}
