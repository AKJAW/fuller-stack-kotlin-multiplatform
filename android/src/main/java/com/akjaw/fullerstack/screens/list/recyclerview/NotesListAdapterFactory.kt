package com.akjaw.fullerstack.screens.list.recyclerview

import helpers.date.PatternProvider
import model.Note
import model.NoteIdentifierMapper

class NotesListAdapterFactory(
    private val patternProvider: PatternProvider,
    private val noteIdentifierMapper: NoteIdentifierMapper,
    private val notesSelectionTrackerFactory: NotesSelectionTrackerFactory
) {
    fun create(
        initialSelectedNotesIds: List<Int>?,
        onItemClicked: (Note) -> Unit
    ): NotesListAdapter {
        val selectedIds = initialSelectedNotesIds ?: emptyList()
        val selectedIdentifiers = noteIdentifierMapper.toIdentifiers(selectedIds)

        return NotesListAdapter(
            initialSelectedNotes = selectedIdentifiers,
            notesSelectionTrackerFactory = notesSelectionTrackerFactory,
            dateFormat = patternProvider.getNotesListItemPattern(),
            onItemClicked = onItemClicked
        )
    }
}
