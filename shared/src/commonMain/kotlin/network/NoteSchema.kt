package network

import kotlinx.serialization.Serializable
import model.CreationTimestamp
import model.LastModificationTimestamp
import model.toCreationTimestamp
import model.toLastModificationTimestamp

@Serializable
data class NoteSchema(
    val apiId: Int = -1,
    val title: String = "",
    val content: String = "",
    val lastModificationTimestamp: LastModificationTimestamp = 0L.toLastModificationTimestamp(),
    val creationTimestamp: CreationTimestamp = 0L.toCreationTimestamp(),
    val wasDeleted: Boolean = false
)
