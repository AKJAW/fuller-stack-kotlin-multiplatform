package store

import features.noteeditor.NoteEditorSlice
import features.noteslist.NotesListSlice

data class AppState(
    val notesListState: NotesListSlice.State = NotesListSlice.State(),
    val noteEditorState: NoteEditorSlice.State = NoteEditorSlice.State()
)
