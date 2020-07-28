package database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
actual data class NoteEntity(
    @PrimaryKey(autoGenerate = true) actual val id: Int = 0,
    actual val noteId: Int,
    actual val title: String,
    actual val content: String,
    actual val lastModificationTimestamp: Long,
    actual val creationTimestamp: Long,
    actual val hasSyncFailed: Boolean = false
)
