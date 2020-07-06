package model

import com.soywiz.klock.DateTime

data class Note(
    val id: Int = -1,
    val title: String = "",
    val content: String = "",
    val creationDate: DateTime = DateTime.now()
)
