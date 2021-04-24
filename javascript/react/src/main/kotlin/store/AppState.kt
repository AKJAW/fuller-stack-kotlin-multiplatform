package store

import features.editor.NoteEditorSlice
import features.list.NotesListSlice
import features.settings.SettingsSlice

data class AppState(
    val notesListState: NotesListSlice.State = NotesListSlice.State(),
    val noteEditorState: NoteEditorSlice.State = NoteEditorSlice.State(),
    val settingsState: SettingsSlice.State = SettingsSlice.State(),
)
