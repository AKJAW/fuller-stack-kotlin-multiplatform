package com.akjaw.fullerstack.screens.list.recyclerview

import androidx.fragment.app.FragmentManager
import helpers.date.PatternProvider
import model.Note

class NotesListAdapterFactory(
    private val patternProvider: PatternProvider
) {
    fun create(
        fragmentManager: FragmentManager,
        onItemClicked: (Note) -> Unit
    ): NotesListAdapter {
        return NotesListAdapter(fragmentManager, patternProvider.getNotesListItemPattern(), onItemClicked)
    }
}
