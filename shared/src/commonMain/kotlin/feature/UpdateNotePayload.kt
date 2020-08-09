package feature

import kotlinx.serialization.Serializable
import model.CreationTimestamp
import model.LastModificationTimestamp

@Serializable
data class UpdateNotePayload(
    @Deprecated("is replaced by creation timestamp") val noteId: Int,
    val title: String,
    val content: String,
    val lastModificationTimestamp: LastModificationTimestamp,
    val creationTimestamp: CreationTimestamp
)
