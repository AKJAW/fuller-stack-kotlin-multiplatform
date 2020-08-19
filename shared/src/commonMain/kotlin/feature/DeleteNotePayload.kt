package feature

import kotlinx.serialization.Serializable
import model.CreationTimestamp

@Serializable
data class DeleteNotePayload(
    val creationTimestamp: CreationTimestamp,
    val wasDeleted: Boolean
)
