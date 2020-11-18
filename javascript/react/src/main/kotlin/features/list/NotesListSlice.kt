package features.list

import DexieNoteDao
import composition.KodeinEntry
import features.list.thunk.GetNotesThunk
import features.list.thunk.SynchronizeNotesThunk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import model.Note
import org.kodein.di.instance
import redux.RAction
import store.RThunk

object NotesListSlice {
    data class State(
        val notesList: Array<Note> = emptyArray(),
        val isLoading: Boolean = true
    )

    private val dexieNoteDao by KodeinEntry.di.instance<DexieNoteDao>()

    private val notesListScope = CoroutineScope(SupervisorJob())
    private val getNotesListThunk = GetNotesThunk(notesListScope, dexieNoteDao)
    private val synchronizeNotesThunk = SynchronizeNotesThunk(GlobalScope, dexieNoteDao)

    init {
        notesListScope.launch {
            dexieNoteDao.initialize()
        }
    }

    fun getNotesList(): RThunk = getNotesListThunk

    fun synchronizeNotes(): RThunk = synchronizeNotesThunk

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
