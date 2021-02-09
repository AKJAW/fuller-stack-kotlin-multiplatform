package helpers.date

import com.soywiz.klock.DateFormat

enum class NoteDateFormat(val value: String) {
    NOTES_LIST_ITEM_MONTH("dd.MM"),
    NOTES_LIST_ITEM_YEAR("dd.MM.yyyy"),
    NOTES_LIST_ITEM_MONTH_HOUR("HH:mm dd.MM"),
    NOTES_LIST_ITEM_YEAR_HOUR("HH:mm dd.MM.yyyy");

    companion object {
        val Default: NoteDateFormat = NOTES_LIST_ITEM_MONTH
    }
}

fun NoteDateFormat.toDateFormat(): DateFormat = DateFormat(this.value)

fun DateFormat.toNoteDateFormat(): NoteDateFormat = NoteDateFormat.values().first { it.value == this.toString() }
