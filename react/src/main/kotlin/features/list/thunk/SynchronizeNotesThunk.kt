package features.list.thunk

import composition.KodeinEntry
import feature.SynchronizeNotes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.kodein.di.instance
import redux.RAction
import redux.WrapperAction
import store.AppState
import store.RThunk
import store.nullAction

class SynchronizeNotesThunk(private val scope: CoroutineScope) : RThunk {
    private val synchronizeNotes by KodeinEntry.di.instance<SynchronizeNotes>()

    override fun invoke(dispatch: (RAction) -> WrapperAction, getState: () -> AppState): WrapperAction {

        scope.launch {
            synchronizeNotes.executeAsync() //TODO do something with the result
        }

        return nullAction
    }
}
