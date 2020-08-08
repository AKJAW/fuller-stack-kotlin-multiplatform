package model

import com.soywiz.klock.DateTime

data class Note(
    val noteIdentifier: NoteIdentifier = NoteIdentifier(-1),
    val title: String = "",
    val content: String = "",
    val lastModificationTimestamp: LastModificationTimestamp = LastModificationTimestamp(DateTime.nowUnixLong()),
    val creationTimestamp: CreationTimestamp = CreationTimestamp(DateTime.nowUnixLong())
)
