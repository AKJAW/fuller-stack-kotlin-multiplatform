package features.editor

import features.editor.thunk.AddNoteThunk
import features.editor.thunk.UpdateNoteThunk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import model.Note
import redux.RAction
import store.RThunk

object NoteEditorSlice { // TODO make an interface?
    data class State(
        val selectedNote: Note? = null,
        val isUpdating: Boolean = false
    )

    private val noteEditorScope = CoroutineScope(SupervisorJob())

    fun addNote(note: Note): RThunk = AddNoteThunk(noteEditorScope, note)

    fun updateNote(note: Note): RThunk = UpdateNoteThunk(noteEditorScope, note)

    data class OpenEditor(val note: Note?) : RAction

    class CloseEditor : RAction

    fun reducer(state: State = State(), action: RAction): State {
        return when (action) {
            is OpenEditor -> {
                val note = action.note ?: Note(id = -1)
                state.copy(selectedNote = note, isUpdating = action.note != null)
            }
            is CloseEditor -> {
                state.copy(selectedNote = null, isUpdating = false)
            }
            else -> state
        }
    }
}