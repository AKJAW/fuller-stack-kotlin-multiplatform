package model.schema

import kotlinx.serialization.Serializable

@Serializable
data class NoteRequest(
    val title: String,
    val content: String
)
