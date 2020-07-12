package features.editor.thunk

import composition.KodeinEntry
import feature.editor.UpdateNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import model.Note
import org.kodein.di.instance
import redux.RAction
import redux.WrapperAction
import store.AppState
import store.RThunk
import store.nullAction

class UpdateNoteThunk(private val scope: CoroutineScope, private val note: Note) : RThunk {
    private val updateNote by KodeinEntry.di.instance<UpdateNote>()

    override fun invoke(dispatch: (RAction) -> WrapperAction, getState: () -> AppState): WrapperAction {
        scope.launch {
            val wasUpdated = updateNote.executeAsync(note)
            if(wasUpdated.not()){
                //TODO
            }
        }
        return nullAction
    }
}
