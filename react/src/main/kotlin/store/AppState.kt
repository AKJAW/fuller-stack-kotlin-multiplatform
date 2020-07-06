package store

import features.editor.NoteEditorSlice
import features.list.NotesListSlice

data class AppState(
    val notesListState: NotesListSlice.State = NotesListSlice.State(),
    val noteEditorState: NoteEditorSlice.State = NoteEditorSlice.State()
)
