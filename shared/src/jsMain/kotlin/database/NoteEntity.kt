package database

actual data class NoteEntity(
    val id: Int,
    val noteId: Int,
    val title: String,
    val content: String,
    val lastModificationTimestamp: Long,
    val creationTimestamp: Long
)
