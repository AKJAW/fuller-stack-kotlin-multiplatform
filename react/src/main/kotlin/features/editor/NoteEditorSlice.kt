package features.editor

import features.editor.thunk.AddNoteThunk
import features.editor.thunk.DeleteNotesThunk
import features.editor.thunk.UpdateNoteThunk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import model.CreationTimestamp
import model.Note
import model.NoteIdentifier
import redux.RAction
import store.RThunk

object NoteEditorSlice {
    data class State(
        val selectedNote: Note? = null,
        val isUpdating: Boolean = false
    )

    private val noteEditorScope = CoroutineScope(SupervisorJob())

    fun addNote(title: String, content: String): RThunk =
        AddNoteThunk(scope = noteEditorScope, title = title, content = content)

    fun updateNote(
        creationTimestamp: CreationTimestamp,
        title: String,
        content: String
    ): RThunk =
        UpdateNoteThunk(noteEditorScope, creationTimestamp, title, content)

    fun deleteNotes(noteIdentifiers: List<NoteIdentifier>): RThunk = DeleteNotesThunk(noteEditorScope, noteIdentifiers)

    data class OpenEditor(val note: Note?) : RAction

    class CloseEditor : RAction

    fun reducer(state: State = State(), action: RAction): State {
        return when (action) {
            is OpenEditor -> {
                val note = action.note ?: Note(noteIdentifier = NoteIdentifier(-1))
                state.copy(selectedNote = note, isUpdating = action.note != null)
            }
            is CloseEditor -> {
                state.copy(selectedNote = null, isUpdating = false)
            }
            else -> state
        }
    }
}
