package database

import kotlinx.coroutines.flow.Flow

interface NoteDao {

    fun getAllNotes(): Flow<List<NoteEntity>>

    suspend fun insertNote(note: NoteEntity): Long

    suspend fun updateNote(
        noteId: Int,
        title: String,
        content: String,
        lastModificationTimestamp: Long
    )

    suspend fun deleteNote(noteId: Int)
}
