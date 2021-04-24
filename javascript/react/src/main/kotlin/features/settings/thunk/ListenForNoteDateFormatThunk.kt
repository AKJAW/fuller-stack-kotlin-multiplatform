package features.settings.thunk

import features.settings.SettingsSlice
import helpers.date.PatternProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import redux.RAction
import redux.WrapperAction
import store.AppState
import store.RThunk
import store.nullAction

class ListenForNoteDateFormatThunk(
    private val scope: CoroutineScope,
    private val patternProvider: PatternProvider
) : RThunk {

    override fun invoke(dispatch: (RAction) -> WrapperAction, getState: () -> AppState): WrapperAction {
        scope.launch {
            patternProvider.patternFlow.collect { dateFormat ->
                dispatch(SettingsSlice.ChangeNoteDateFormat(dateFormat))
            }
        }

        return nullAction
    }
}
