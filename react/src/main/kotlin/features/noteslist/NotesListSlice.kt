package features.noteslist

import data.Note
import features.noteslist.thunk.FetchNotesListThunk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import redux.RAction
import store.RThunk

object NotesListSlice {
    data class State(
        val notesList: Array<Note> = emptyArray(),
        val isLoading: Boolean = true
    )

    private val notesListScope = CoroutineScope(SupervisorJob())
    private val fetchNotesListThunk = FetchNotesListThunk(notesListScope)

    fun fetchNotesList(): RThunk = fetchNotesListThunk

    class SetNotesList(val notesList: Array<Note>) : RAction

    class SetIsLoading(val isLoading: Boolean) : RAction

    fun reducer(state: State = State(), action: RAction): State {
        return when (action) {
            is SetNotesList -> {
                state.copy(notesList = action.notesList)
            }
            is SetIsLoading -> {
                state.copy(isLoading = action.isLoading)
            }
            else -> state
        }
    }
}
