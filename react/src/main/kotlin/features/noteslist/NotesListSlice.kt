package features.noteslist

import base.usecase.Either
import base.usecase.Failure
import base.usecase.UseCaseAsync
import data.Note
import dependencyinjection.KodeinEntry.di
import feature.noteslist.AddNote
import feature.noteslist.FetchNotes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kodein.di.instance
import redux.RAction
import redux.WrapperAction
import store.RThunk
import store.State
import store.nullAction

object NotesListSlice {
    private val fetchNotesListUseCaseAsync by di.instance<FetchNotes>()
    private val addNoteUseCaseAsync by di.instance<AddNote>()
    private val notesListScope = CoroutineScope(SupervisorJob())
    private var notesFlowJob: Job? = null

    //TODO should this be cancelled somewhere?
    fun fetchNotesList(): RThunk = object : RThunk {
        override fun invoke(dispatch: (RAction) -> WrapperAction, getState: () -> State): WrapperAction {
            if(notesFlowJob != null) return nullAction
            console.log("fetchNotesList")
            notesListScope.launch {
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
            notesFlowJob = notesListScope.launch {
                notesFlow.collect { notes ->
                    val action = SetNotesList(notes.toTypedArray())
                    dispatch(action)
                }
            }
        }
    }

    fun addNote(note: Note): RThunk = object : RThunk {
        override fun invoke(dispatch: (RAction) -> WrapperAction, getState: () -> State): WrapperAction {
            notesListScope.launch {
                addNoteUseCaseAsync.executeAsync(
                    note
                ) { result -> handleResult(dispatch, result) }
            }

            return nullAction
        }

        private fun handleResult(dispatch: (RAction) -> WrapperAction, result: Either<Failure, UseCaseAsync.None>) {
            when (result) {
                is Either.Left -> TODO()
                is Either.Right -> {/* empty */}
            }
        }
    }

    class SetNotesList(val notesList: Array<Note>) : RAction

    fun reducer(state: Array<Note> = emptyArray(), action: RAction): Array<Note> {
        return when (action) {
            is SetNotesList -> {
                action.notesList
            }
            else -> state
        }
    }
}
