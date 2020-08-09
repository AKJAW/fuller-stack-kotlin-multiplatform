package model

import com.soywiz.klock.DateTime

data class Note(
    val title: String = "",
    val content: String = "",
    val lastModificationTimestamp: LastModificationTimestamp = LastModificationTimestamp(DateTime.nowUnixLong()),
    val creationTimestamp: CreationTimestamp = CreationTimestamp(DateTime.nowUnixLong())
)
