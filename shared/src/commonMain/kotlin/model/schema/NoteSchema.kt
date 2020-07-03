package model.schema

import kotlinx.serialization.Serializable

@Serializable
data class NoteSchema(
    val id: Int = -1,
    val title: String = "",
    val content: String = "",
    val creationDateTimestamp: Long = 0
)