package features.list.thunk

import DexieNoteDao
import composition.KodeinEntry
import feature.synchronization.SynchronizeNotes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kodein.di.instance
import redux.RAction
import redux.WrapperAction
import store.AppState
import store.RThunk
import store.nullAction

@Suppress("MagicNumber")
class SynchronizeNotesThunk(
    private val scope: CoroutineScope,
    private val dexieNoteDao: DexieNoteDao
) : RThunk {
    private val synchronizeNotes by KodeinEntry.di.instance<SynchronizeNotes>()

    override fun invoke(dispatch: (RAction) -> WrapperAction, getState: () -> AppState): WrapperAction {
        scope.launch {
            while (dexieNoteDao.isInitialized.not()) {
                delay(100)
            }
            synchronizeNotes.executeAsync()
        }

        return nullAction
    }
}
