package com.akjaw.fullerstack.screens.list.recyclerview

import helpers.date.PatternProvider
import model.Note

class NotesListAdapterFactory(
    private val patternProvider: PatternProvider
) {
    fun create(onItemClicked: (Note) -> Unit): NotesListAdapter {
        return NotesListAdapter(onItemClicked, patternProvider.getNotesListItemPattern())
    }
}
