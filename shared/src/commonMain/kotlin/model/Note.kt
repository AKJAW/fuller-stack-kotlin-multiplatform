package model

import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTime
import helpers.date.NoteDateFormat

data class Note(
    val title: String = "",
    val content: String = "",
    val lastModificationTimestamp: LastModificationTimestamp = DateTime.nowUnixLong().toLastModificationTimestamp(),
    val creationTimestamp: CreationTimestamp = DateTime.nowUnixLong().toCreationTimestamp(),
    val dateFormat: DateFormat = NoteDateFormat.Default,
    val hasSyncFailed: Boolean = false
)
