package features.editor.thunk

import composition.KodeinEntry
import feature.NewUpdateNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import model.NoteIdentifier
import org.kodein.di.instance
import redux.RAction
import redux.WrapperAction
import store.AppState
import store.RThunk
import store.nullAction

class UpdateNoteThunk(
    private val scope: CoroutineScope,
    private val noteIdentifier: NoteIdentifier,
    private val title: String,
    private val content: String
) : RThunk {
    private val updateNote by KodeinEntry.di.instance<NewUpdateNote>()

    override fun invoke(dispatch: (RAction) -> WrapperAction, getState: () -> AppState): WrapperAction {
        scope.launch {
            val wasUpdated = updateNote.executeAsync(
                noteIdentifier = noteIdentifier,
                title = title,
                content = content
            )
            if (wasUpdated.not()) {
                // TODO
            }
        }
        return nullAction
    }
}
