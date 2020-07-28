package database

expect class NoteEntity {
    val id: Int
    val noteId: Int
    val title: String
    val content: String
    val lastModificationTimestamp: Long
    val creationTimestamp: Long
    val hasSyncFailed: Boolean
}
