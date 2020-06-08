package data

import com.soywiz.klock.DateTime

data class Note(
    val title: String = "",
    val creationDate: DateTime = DateTime.now()
)
