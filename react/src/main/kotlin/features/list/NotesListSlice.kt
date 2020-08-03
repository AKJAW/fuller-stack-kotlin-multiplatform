package features.list

import features.list.thunk.GetNotesListThunk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import model.Note
import redux.RAction
import store.RThunk

object NotesListSlice {
    data class State(
        val notesList: Array<Note> = emptyArray(),
        val isLoading: Boolean = true
    )

    private val notesListScope = CoroutineScope(SupervisorJob())
    private val getNotesListThunk = GetNotesListThunk(notesListScope)

    fun getNotesList(): RThunk = getNotesListThunk

    class SetNotesList(val notesList: Array<Note>) : RAction

    class SetIsLoading(val isLoading: Boolean) : RAction

    fun reducer(state: State = State(), action: RAction): State {
        return when (action) {
            is SetNotesList -> {
                state.copy(notesList = action.notesList, isLoading = false)
            }
            is SetIsLoading -> {
                state.copy(isLoading = action.isLoading)
            }
            else -> state
        }
    }
}
