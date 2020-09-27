package database

import model.CreationTimestamp
import model.LastModificationTimestamp

actual data class NoteEntity(
    actual val localId: Int = -1,
    actual val title: String,
    actual val content: String,
    actual val lastModificationTimestamp: LastModificationTimestamp,
    actual val creationTimestamp: CreationTimestamp,
    actual val hasSyncFailed: Boolean = false,
    actual val wasDeleted: Boolean = false
)
