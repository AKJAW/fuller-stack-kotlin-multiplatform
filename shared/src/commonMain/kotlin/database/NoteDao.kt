package database

import kotlinx.coroutines.flow.Flow

interface NoteDao {

    fun getAllNotes(): Flow<List<NoteEntity>>

    suspend fun addNote(note: NoteEntity): Int

    suspend fun updateNote(
        noteId: Int,
        title: String,
        content: String,
        lastModificationTimestamp: Long
    )

    suspend fun updateId(
        localId: Int,
        apiId: Int
    )

    suspend fun updateSyncFailed(noteId: Int, hasSyncFailed: Boolean)

    @Deprecated("Use deleteNotes")
    suspend fun deleteNote(noteId: Int)

    suspend fun deleteNotes(noteIds: List<Int>)

    suspend fun setWasDeleted(noteIds: List<Int>)
}
