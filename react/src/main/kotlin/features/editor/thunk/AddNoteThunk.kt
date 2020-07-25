package features.editor.thunk

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
            val wasAdded = addNote.executeAsync(note)
            if (wasAdded.not()) {
                // TODO
            }
        }

        return nullAction
    }
}
