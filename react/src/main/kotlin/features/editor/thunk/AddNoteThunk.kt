package features.editor.thunk

import composition.KodeinEntry
import feature.NewAddNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.kodein.di.instance
import redux.RAction
import redux.WrapperAction
import store.AppState
import store.RThunk
import store.nullAction

class AddNoteThunk(
    private val scope: CoroutineScope,
    private val title: String,
    private val content: String
) : RThunk {
    private val addNote by KodeinEntry.di.instance<NewAddNote>()

    override fun invoke(dispatch: (RAction) -> WrapperAction, getState: () -> AppState): WrapperAction {
        scope.launch {
            val wasAdded = addNote.executeAsync(title, content)
            if (wasAdded.not()) {
                // TODO
            }
        }

        return nullAction
    }
}
