package features.noteslist.thunk

import base.usecase.Either
import base.usecase.Failure
import base.usecase.UseCaseAsync
import data.Note
import dependencyinjection.KodeinEntry
import feature.noteslist.FetchNotes
import features.noteslist.NotesListSlice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kodein.di.instance
import redux.RAction
import redux.WrapperAction
import store.RThunk
import store.State
import store.nullAction

class FetchNotesListThunk(private val scope: CoroutineScope) : RThunk {
    private val fetchNotesListUseCaseAsync by KodeinEntry.di.instance<FetchNotes>()
    private var notesFlowJob: Job? = null

    //TODO should this be cancelled somewhere?
    override fun invoke(dispatch: (RAction) -> WrapperAction, getState: () -> State): WrapperAction {
        if(notesFlowJob != null) return nullAction
        console.log("fetchNotesList")
        scope.launch {
            fetchNotesListUseCaseAsync.executeAsync(
                UseCaseAsync.None()
            ) { result -> handleResult(dispatch, result) }
        }

        return nullAction // TODO is this necessary
    }

    private fun handleResult(dispatch: (RAction) -> WrapperAction, result: Either<Failure, Flow<List<Note>>>) =
        when (result) {
            is Either.Left -> TODO()
            is Either.Right -> listenToNoteChanges(dispatch, result.r)
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
