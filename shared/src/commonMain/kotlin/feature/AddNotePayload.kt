package feature

import kotlinx.serialization.Serializable
import model.CreationTimestamp
import model.LastModificationTimestamp

@Serializable
data class AddNotePayload(
    val title: String,
    val content: String,
    val creationTimestamp: CreationTimestamp,
    val lastModificationTimestamp: LastModificationTimestamp
)
