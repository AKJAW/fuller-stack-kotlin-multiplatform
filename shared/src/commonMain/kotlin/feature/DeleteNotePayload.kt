package feature

import kotlinx.serialization.Serializable
import model.CreationTimestamp
import model.LastModificationTimestamp

@Serializable
data class DeleteNotePayload(
    val creationTimestamp: CreationTimestamp,
    val wasDeleted: Boolean,
    val lastModificationTimestamp: LastModificationTimestamp
)
