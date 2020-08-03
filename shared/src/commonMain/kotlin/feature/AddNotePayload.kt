package feature

import kotlinx.serialization.Serializable

@Serializable
data class AddNotePayload(
    val title: String,
    val content: String,
    val currentTimestamp: Long
)
