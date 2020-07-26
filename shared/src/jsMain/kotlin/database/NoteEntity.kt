package database

actual data class NoteEntity(
    val id: Int = -1,
    val noteId: Int,
    val title: String,
    val content: String,
    val lastModificationTimestamp: Long,
    val creationTimestamp: Long
)
