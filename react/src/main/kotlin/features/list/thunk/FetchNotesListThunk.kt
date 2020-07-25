package features.list.thunk

import composition.KodeinEntry
import feature.list.FetchNotes
import features.list.NotesListSlice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import model.Note
import org.kodein.di.instance
import redux.RAction
import redux.WrapperAction
import store.AppState
import store.RThunk
import store.nullAction

class FetchNotesListThunk(private val scope: CoroutineScope) : RThunk {
    private val fetchNotes by KodeinEntry.di.instance<FetchNotes>()
    private var notesFlowJob: Job? = null

    // TODO should this be cancelled somewhere?
    override fun invoke(dispatch: (RAction) -> WrapperAction, getState: () -> AppState): WrapperAction {
        if (notesFlowJob != null) return nullAction

        scope.launch {
            fetchNotes.executeAsync().collect {
                handleFetchNotesResult(dispatch, it)
            }
        }

        return nullAction // TODO is this necessary
    }

    private fun handleFetchNotesResult(dispatch: (RAction) -> WrapperAction, fetchNotesResult: FetchNotes.Result) {
        when (fetchNotesResult) {
            FetchNotes.Result.Loading -> dispatch(NotesListSlice.SetIsLoading(true))
            is FetchNotes.Result.Error -> TODO()
            is FetchNotes.Result.Content -> {
                dispatch(NotesListSlice.SetIsLoading(false))
                listenToNoteChanges(dispatch, fetchNotesResult.notesFlow)
            }
        }
    }

    private fun listenToNoteChanges(
        dispatch: (RAction) -> WrapperAction,
        notesFlow: Flow<List<Note>>
    ) {
        notesFlowJob = scope.launch { // TODO handle error
            notesFlow.collect { notes ->
                val action = NotesListSlice.SetNotesList(notes.toTypedArray())
                dispatch(action)
            }
        }
    }
}
