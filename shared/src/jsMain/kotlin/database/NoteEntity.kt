package database

actual data class NoteEntity(
    actual val id: Int = -1,
    actual val noteId: Int,
    actual val title: String,
    actual val content: String,
    actual val lastModificationTimestamp: Long,
    actual val creationTimestamp: Long,
    actual val hasSyncFailed: Boolean = false,
    actual val wasDeleted: Boolean = false
)
