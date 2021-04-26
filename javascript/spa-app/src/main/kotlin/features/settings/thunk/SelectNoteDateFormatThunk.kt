package features.settings.thunk

import com.soywiz.klock.DateFormat
import helpers.date.PatternSaver
import redux.RAction
import redux.WrapperAction
import store.AppState
import store.RThunk
import store.nullAction

class SelectNoteDateFormatThunk(
    private val patternSaver: PatternSaver,
    private val dateFormat: DateFormat
) : RThunk {

    override fun invoke(dispatch: (RAction) -> WrapperAction, getState: () -> AppState): WrapperAction {
        patternSaver.setPattern(dateFormat)

        return nullAction
    }
}
