package database

import model.CreationTimestamp
import model.LastModificationTimestamp

expect class NoteEntity {
    val id: Int
    val noteId: Int
    val title: String
    val content: String
    val lastModificationTimestamp: LastModificationTimestamp
    val creationTimestamp: CreationTimestamp
    val hasSyncFailed: Boolean
    val wasDeleted: Boolean
}
