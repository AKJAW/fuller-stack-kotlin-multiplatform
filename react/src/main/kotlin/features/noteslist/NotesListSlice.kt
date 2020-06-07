package features.noteslist

import base.usecase.Either
import base.usecase.Failure
import base.usecase.UseCaseAsync
import data.Note
import dependencyinjection.KodeinEntry.di
import feature.noteslist.FetchNotesListUseCaseAsync
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.instance
import redux.RAction
import redux.WrapperAction
import store.RThunk
import store.State
import store.nullAction

object NotesListSlice {
    val fetchNotesListUseCaseAsync by di.instance<FetchNotesListUseCaseAsync>()

    fun fetchNotesList(): RThunk = object : RThunk {
        override fun invoke(dispatch: (RAction) -> WrapperAction, getState: () -> State): WrapperAction {
            console.log("fetchNotesList")
            GlobalScope.launch {
                fetchNotesListUseCaseAsync.executeAsync(
                    UseCaseAsync.None()
                ) { result -> handleResult(dispatch, result) }
            }

            return nullAction // TODO is this necessary
        }

        fun handleResult(dispatch: (RAction) -> WrapperAction, result: Either<Failure, List<Note>>) =
            when (result) {
                is Either.Left -> TODO()
                is Either.Right -> {
                    val action = SetNotesList(result.r.toTypedArray())
                    dispatch(action)
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
