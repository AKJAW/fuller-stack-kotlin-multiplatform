package helpers.date

import com.soywiz.klock.DateFormat
import helpers.storage.Storage

// TODO some validation
class PatternProvider(private val storage: Storage) {
    companion object {
        private const val NOTES_LIST_ITEM_PATTERN = "NOTES_LIST_ITEM_PATTERN"
        private const val NOTES_LIST_ITEM_DEFAULT = "dd.MM"
    }

    fun getNotesListItemPattern(): DateFormat =
        getOrDefault(NOTES_LIST_ITEM_PATTERN, NOTES_LIST_ITEM_DEFAULT)

    private fun getOrDefault(key: String, defaultPattern: String): DateFormat {
        val pattern = storage.getString(key) ?: defaultPattern
        return DateFormat(pattern)
    }
}
