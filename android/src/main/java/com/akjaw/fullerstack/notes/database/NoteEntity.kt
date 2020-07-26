package com.akjaw.fullerstack.notes.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.soywiz.klock.DateTime

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val noteId: Int,
    val title: String,
    val content: String,
    val lastModificationTimestamp: Long,
    val creationTimestamp: Long = DateTime.nowUnixLong()
)
