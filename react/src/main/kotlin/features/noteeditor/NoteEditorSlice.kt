package features.noteeditor

import data.Note
import features.noteeditor.thunk.AddNoteThunk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import redux.RAction
import store.RThunk

object NoteEditorSlice { //TODO make an interface?
    data class State(
        val selectedNote: Note? = null,
        val isEditing: Boolean = false
    )

    private val noteEditorScope = CoroutineScope(SupervisorJob())

    fun addNote(note: Note): RThunk = AddNoteThunk(noteEditorScope, note)

    data class OpenEditor(val note: Note?): RAction

    class CloseEditor: RAction

    fun reducer(state: State = State(), action: RAction): State {
        return when (action) {
            is OpenEditor -> {
                val note = action.note ?: Note(id = -1)
                state.copy(selectedNote = note, isEditing = action.note != null)
            }
            is CloseEditor -> {
                state.copy(selectedNote = null, isEditing = false)
            }
            else -> state
        }
    }
}
