package helpers.date

import com.soywiz.klock.DateFormat
import helpers.storage.Storage
import kotlinx.coroutines.flow.MutableStateFlow

internal class PatternStorage(
    private val storage: Storage,
    private val notesDatePatternStorageKey: NotesDatePatternStorageKey
) : PatternProvider, PatternSaver {

    override val patternFlow: MutableStateFlow<DateFormat> = MutableStateFlow(getSavedPattern())

    override fun getPattern(): DateFormat = patternFlow.value

    override fun setPattern(dateFormat: DateFormat) {
        storage.setString(notesDatePatternStorageKey.value, dateFormat.toString())
        patternFlow.value = dateFormat
    }

    private fun getSavedPattern(): DateFormat {
        val pattern = storage.getString(notesDatePatternStorageKey.value) ?: return initializeDefault()
        return DateFormat(pattern)
    }

    private fun initializeDefault(): DateFormat {
        val defaultFormat = NoteDateFormat.Default.toDateFormat()
        storage.setString(notesDatePatternStorageKey.value, defaultFormat.toString())
        return defaultFormat
    }
}
