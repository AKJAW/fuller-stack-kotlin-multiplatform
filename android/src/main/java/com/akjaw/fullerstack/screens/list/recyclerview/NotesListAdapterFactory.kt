package com.akjaw.fullerstack.screens.list.recyclerview

import androidx.fragment.app.FragmentActivity
import helpers.date.PatternProvider
import model.Note
import model.NoteIdentifierMapper

class NotesListAdapterFactory(
    private val patternProvider: PatternProvider,
    private val noteIdentifierMapper: NoteIdentifierMapper
) {
    fun create(
        activity: FragmentActivity,
        initialSelectedNotesIds: List<Int>?,
        onItemClicked: (Note) -> Unit
    ): NotesListAdapter {
        val selectedIds = initialSelectedNotesIds ?: emptyList()
        val selectedIdentifiers = noteIdentifierMapper.toIdentifiers(selectedIds)
        val notesSelectionTrackerFactory = NotesSelectionTrackerFactory(
            activity = activity,
            initialSelectedNotes = selectedIdentifiers,
            noteIdentifierMapper = noteIdentifierMapper
        )

        return NotesListAdapter(
            notesSelectionTrackerFactory = notesSelectionTrackerFactory,
            dateFormat = patternProvider.getNotesListItemPattern(),
            onItemClicked = onItemClicked
        )
    }
}
