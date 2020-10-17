package features.list.thunk

import DexieNoteDao
import composition.KodeinEntry
import feature.GetNotes
import features.list.NotesListSlice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kodein.di.instance
import redux.RAction
import redux.WrapperAction
import store.AppState
import store.RThunk
import store.nullAction

class GetNotesThunk(
    private val scope: CoroutineScope,
    private val dexieNoteDao: DexieNoteDao
) : RThunk {
    private val getNotes by KodeinEntry.di.instance<GetNotes>()
    private var notesFlowJob: Job? = null

    // TODO should this be cancelled somewhere?
    override fun invoke(dispatch: (RAction) -> WrapperAction, getState: () -> AppState): WrapperAction {
        if (notesFlowJob != null) return nullAction

        scope.launch {
            while (dexieNoteDao.isInitialized.not()) {
                delay(100)
            }
            getNotes.executeAsync().collect { notes ->
                val action = NotesListSlice.SetNotesList(notes.toTypedArray())
                dispatch(action)
            }
        }

        return nullAction
    }
}
