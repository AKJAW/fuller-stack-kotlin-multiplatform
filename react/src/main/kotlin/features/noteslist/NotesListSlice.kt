package features.noteslist

import data.Note
import dependencyinjection.KodeinEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.erased.instance
import redux.RAction
import redux.WrapperAction
import store.RThunk
import store.State
import store.nullAction
import usecases.FetchNotesListUseCaseAsync


object NotesListSlice {
    val fetchNotesListUseCaseAsync by KodeinEntry.kodein.instance<FetchNotesListUseCaseAsync>()

    fun fetchNotesList(): RThunk = object : RThunk {
        override fun invoke(dispatch: (RAction) -> WrapperAction, getState: () -> State): WrapperAction {
            console.log("fetchNotesList")
            GlobalScope.launch {
                val result = fetchNotesListUseCaseAsync.executeAsync(Dispatchers.Default)

                when(result){
                    is FetchNotesListUseCaseAsync.FetchNotesListResult.Success -> {
                        val action = SetNotesList(result.notes.toTypedArray())
                        dispatch(action)
                    }
                    FetchNotesListUseCaseAsync.FetchNotesListResult.Failure -> TODO()
                }
            }
            return nullAction //TODO is this necessary
        }
    }

    class SetNotesList(val notesList: Array<Note>): RAction

    fun reducer(state: Array<Note> = emptyArray(), action: RAction): Array<Note> {
        return when(action) {
            is SetNotesList -> {
                action.notesList
            }
            else -> state
        }
    }
}
