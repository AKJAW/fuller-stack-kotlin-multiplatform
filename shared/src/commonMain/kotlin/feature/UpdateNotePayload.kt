package feature

import kotlinx.serialization.Serializable
import model.CreationTimestamp
import model.LastModificationTimestamp

@Serializable
data class UpdateNotePayload(
    val title: String,
    val content: String,
    val lastModificationTimestamp: LastModificationTimestamp,
    val creationTimestamp: CreationTimestamp
)
