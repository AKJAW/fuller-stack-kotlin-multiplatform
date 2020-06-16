package features.noteeditor

import data.Note
import features.noteeditor.thunk.AddNoteThunk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import redux.RAction
import store.RThunk

object NoteEditorSlice { //TODO make an interface?
    data class State(
        val selectedNote: Note? = null
    )

    private val noteEditorScope = CoroutineScope(SupervisorJob())

    fun addNote(note: Note): RThunk = AddNoteThunk(noteEditorScope, note)

    data class SelectNote(val note: Note): RAction

    class UnselectNote: RAction

    fun reducer(state: State = State(), action: RAction): State {
        return when (action) {
            is SelectNote -> {
                state.copy(selectedNote = action.note)
            }
            is UnselectNote -> {
                state.copy(selectedNote = null)
            }
            else -> state
        }
    }
}
