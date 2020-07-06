package features.editor.thunk

import base.usecase.Either
import base.usecase.Failure
import base.usecase.UseCaseAsync
import composition.KodeinEntry
import feature.editor.AddNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import model.Note
import org.kodein.di.instance
import redux.RAction
import redux.WrapperAction
import store.AppState
import store.RThunk
import store.nullAction

class AddNoteThunk(private val scope: CoroutineScope, private val note: Note) : RThunk {
    private val addNote by KodeinEntry.di.instance<AddNote>()

    override fun invoke(dispatch: (RAction) -> WrapperAction, getState: () -> AppState): WrapperAction {
        scope.launch {
            addNote.executeAsync(
                note
            ) { result -> handleResult(dispatch, result) }
        }

        return nullAction
    }

    private fun handleResult(dispatch: (RAction) -> WrapperAction, result: Either<Failure, UseCaseAsync.None>) {
        when (result) {
            is Either.Left -> TODO()
            is Either.Right -> { /* empty */ }
        }
    }
}
