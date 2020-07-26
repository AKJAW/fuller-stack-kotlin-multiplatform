package database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
actual data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val noteId: Int,
    val title: String,
    val content: String,
    val lastModificationTimestamp: Long,
    val creationTimestamp: Long
)
