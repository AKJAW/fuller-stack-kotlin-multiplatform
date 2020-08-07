package database

import androidx.room.Entity
import androidx.room.PrimaryKey
import model.CreationTimestamp
import model.LastModificationTimestamp

@Entity(tableName = "notes")
actual data class NoteEntity(
    @PrimaryKey(autoGenerate = true) actual val id: Int = 0,
    actual val noteId: Int,
    actual val title: String,
    actual val content: String,
    actual val lastModificationTimestamp: LastModificationTimestamp,
    actual val creationTimestamp: CreationTimestamp,
    actual val hasSyncFailed: Boolean = false,
    actual val wasDeleted: Boolean = false
)
