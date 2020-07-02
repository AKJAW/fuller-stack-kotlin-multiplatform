package data

import com.soywiz.klock.DateTime
// TODO rename package to model
data class Note(
    val id: Int = -1,
    val title: String = "",
    val content: String = "",
    val creationDate: DateTime = DateTime.now()
)
