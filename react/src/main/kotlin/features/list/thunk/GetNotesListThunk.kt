package features.list.thunk

import composition.KodeinEntry
import feature.NewGetNotes
import features.list.NotesListSlice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kodein.di.instance
import redux.RAction
import redux.WrapperAction
import store.AppState
import store.RThunk
import store.nullAction

class GetNotesListThunk(private val scope: CoroutineScope) : RThunk {
    private val getNotes by KodeinEntry.di.instance<NewGetNotes>()
    private var notesFlowJob: Job? = null

    // TODO should this be cancelled somewhere?
    override fun invoke(dispatch: (RAction) -> WrapperAction, getState: () -> AppState): WrapperAction {
        if (notesFlowJob != null) return nullAction

        scope.launch {
            getNotes.executeAsync().collect { notes ->
                val action = NotesListSlice.SetNotesList(notes.toTypedArray())
                dispatch(action)
            }
        }

        return nullAction // TODO is this necessary
    }
}
