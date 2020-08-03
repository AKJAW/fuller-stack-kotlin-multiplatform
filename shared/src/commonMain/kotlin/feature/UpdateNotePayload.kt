package feature

import kotlinx.serialization.Serializable

@Serializable
data class UpdateNotePayload(
    val noteId: Int,
    val title: String,
    val content: String,
    val lastModificationTimestamp: Long
)
