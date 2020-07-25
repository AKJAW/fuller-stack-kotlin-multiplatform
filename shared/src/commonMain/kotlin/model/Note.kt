package model

import com.soywiz.klock.DateTime

data class Note(
    val noteIdentifier: NoteIdentifier = NoteIdentifier(-1),
    val title: String = "",
    val content: String = "",
    val creationDate: DateTime = DateTime.now()
)
