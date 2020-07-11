package features.list.thunk

import base.usecase.Either
import base.usecase.Failure
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

        dispatch(NotesListSlice.SetIsLoading(true))
        scope.launch {
            val result = fetchNotes.executeAsync()
            handleResult(dispatch, result)
        }

        return nullAction // TODO is this necessary
    }

    private fun handleResult(dispatch: (RAction) -> WrapperAction, result: Either<Failure, Flow<List<Note>>>) {
        dispatch(NotesListSlice.SetIsLoading(false))
        when (result) {
            is Either.Left -> TODO()
            is Either.Right -> listenToNoteChanges(dispatch, result.r)
        }
    }

    private fun listenToNoteChanges(
        dispatch: (RAction) -> WrapperAction,
        notesFlow: Flow<List<Note>>
    ) {
        notesFlowJob = scope.launch {
            notesFlow.collect { notes ->
                val action = NotesListSlice.SetNotesList(notes.toTypedArray())
                dispatch(action)
            }
        }
    }
}
