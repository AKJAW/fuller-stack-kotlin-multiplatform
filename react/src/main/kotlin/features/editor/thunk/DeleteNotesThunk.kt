package features.editor.thunk

import composition.KodeinEntry
import feature.DeleteNotes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import model.NoteIdentifier
import org.kodein.di.instance
import redux.RAction
import redux.WrapperAction
import store.AppState
import store.RThunk
import store.nullAction

class DeleteNotesThunk(private val scope: CoroutineScope, private val noteIdentifiers: List<NoteIdentifier>) : RThunk {
    private val deleteNotes by KodeinEntry.di.instance<DeleteNotes>()

    override fun invoke(dispatch: (RAction) -> WrapperAction, getState: () -> AppState): WrapperAction {
        scope.launch {
            deleteNotes.executeAsync(noteIdentifiers)
        }
        return nullAction
    }
}
