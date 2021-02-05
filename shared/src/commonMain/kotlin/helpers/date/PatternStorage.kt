package helpers.date

import com.soywiz.klock.DateFormat
import helpers.storage.Storage
import kotlinx.coroutines.flow.MutableStateFlow

internal class PatternStorage(private val storage: Storage): PatternProvider, PatternSaver {

    companion object {
        private const val NOTES_LIST_ITEM_PATTERN_KEY = "NOTES_LIST_ITEM_PATTERN"
    }

    override val patternFlow: MutableStateFlow<DateFormat> = MutableStateFlow(getNotesListItemPattern())

    private fun getNotesListItemPattern(): DateFormat {
        val pattern = storage.getString(NOTES_LIST_ITEM_PATTERN_KEY) ?: return NoteDateFormat.Default
        return DateFormat(pattern)
    }

    override fun setPattern(noteDateFormat: NoteDateFormat) {
        storage.setString(NOTES_LIST_ITEM_PATTERN_KEY, noteDateFormat.value)
    }
}
