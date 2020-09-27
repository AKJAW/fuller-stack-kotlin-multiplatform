package features.editor.thunk

import composition.KodeinEntry
import feature.DeleteNotes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import model.CreationTimestamp
import org.kodein.di.instance
import redux.RAction
import redux.WrapperAction
import store.AppState
import store.RThunk
import store.nullAction

class DeleteNotesThunk(
    private val scope: CoroutineScope,
    private val creationTimestamps: List<CreationTimestamp>
) : RThunk {
    private val deleteNotes by KodeinEntry.di.instance<DeleteNotes>()

    override fun invoke(dispatch: (RAction) -> WrapperAction, getState: () -> AppState): WrapperAction {
        scope.launch {
            deleteNotes.executeAsync(creationTimestamps)
        }
        return nullAction
    }
}
