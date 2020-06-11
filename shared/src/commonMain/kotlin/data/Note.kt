package data

import com.soywiz.klock.DateTime
//TODO rename package to model
data class Note(
    val title: String = "",
    val creationDate: DateTime = DateTime.now()
)
