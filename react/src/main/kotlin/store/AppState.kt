package store

import features.noteslist.NotesListSlice

data class AppState(
    val notesListState: NotesListSlice.State = NotesListSlice.State()
)
