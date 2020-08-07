package network

import kotlinx.serialization.Serializable
import model.CreationTimestamp
import model.LastModificationTimestamp

@Serializable
data class NoteSchema(
    val apiId: Int = -1,
    val title: String = "",
    val content: String = "",
    val lastModificationTimestamp: LastModificationTimestamp = LastModificationTimestamp(0),
    val creationTimestamp: CreationTimestamp = CreationTimestamp(0)
)
