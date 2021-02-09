package features.settings

import com.soywiz.klock.DateFormat
import composition.KodeinEntry
import features.settings.thunk.ListenForNoteDateFormatThunk
import features.settings.thunk.SelectNoteDateFormatThunk
import helpers.date.NoteDateFormat
import helpers.date.PatternProvider
import helpers.date.PatternSaver
import helpers.date.toDateFormat
import helpers.date.toNoteDateFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.kodein.di.instance
import redux.RAction
import store.RThunk

object SettingsSlice {
    data class State(
        val selectedNoteDateFormat: NoteDateFormat = NoteDateFormat.Default
    )

    private val patternStorage by KodeinEntry.di.instance<PatternProvider>()
    private val patternSaver by KodeinEntry.di.instance<PatternSaver>()

    private val settingsScope = CoroutineScope(SupervisorJob())
    private val listenForNoteDateFormatThunk by lazy {
        ListenForNoteDateFormatThunk(settingsScope, patternStorage)
    }

    fun listenToNoteDateFormatChanges(): RThunk = listenForNoteDateFormatThunk

    fun changeNoteDateFormat(noteDateFormat: NoteDateFormat): RThunk =
        SelectNoteDateFormatThunk(patternSaver, noteDateFormat.toDateFormat())

    data class ChangeNoteDateFormat(val dateFormat: DateFormat) : RAction

    fun reducer(state: State = State(), action: RAction): State {
        return when (action) {
            is ChangeNoteDateFormat -> {
                state.copy(selectedNoteDateFormat = action.dateFormat.toNoteDateFormat())
            }
            else -> state
        }
    }
}
