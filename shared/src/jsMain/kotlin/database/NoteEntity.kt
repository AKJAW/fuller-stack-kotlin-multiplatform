package database

import model.CreationTimestamp
import model.LastModificationTimestamp

actual data class NoteEntity(
    actual val id: Int = -1,
    actual val noteId: Int,
    actual val title: String,
    actual val content: String,
    actual val lastModificationTimestamp: LastModificationTimestamp,
    actual val creationTimestamp: CreationTimestamp,
    actual val hasSyncFailed: Boolean = false,
    actual val wasDeleted: Boolean = false
)
