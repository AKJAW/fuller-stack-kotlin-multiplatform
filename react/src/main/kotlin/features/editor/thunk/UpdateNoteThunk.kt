package features.editor.thunk

import composition.KodeinEntry
import feature.UpdateNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import model.CreationTimestamp
import org.kodein.di.instance
import redux.RAction
import redux.WrapperAction
import store.AppState
import store.RThunk
import store.nullAction

class UpdateNoteThunk(
    private val scope: CoroutineScope,
    private val creationTimestamp: CreationTimestamp,
    private val title: String,
    private val content: String
) : RThunk {
    private val updateNote by KodeinEntry.di.instance<UpdateNote>()

    override fun invoke(dispatch: (RAction) -> WrapperAction, getState: () -> AppState): WrapperAction {
        scope.launch {
            updateNote.executeAsync(
                creationTimestamp = creationTimestamp,
                title = title,
                content = content
            )
        }
        return nullAction
    }
}
