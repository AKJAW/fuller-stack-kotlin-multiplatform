package feature

import kotlinx.serialization.Serializable
import model.LastModificationTimestamp

@Serializable
data class UpdateNotePayload(
    val noteId: Int,
    val title: String,
    val content: String,
    val lastModificationTimestamp: LastModificationTimestamp
)
