package model.schema

import kotlinx.serialization.Serializable

@Serializable
data class NoteSchema(
    val apiId: Int = -1, //TODO rename?
    val title: String = "",
    val content: String = "",
    val lastModificationTimestamp: Long = 0,
    val creationTimestamp: Long = 0
)
