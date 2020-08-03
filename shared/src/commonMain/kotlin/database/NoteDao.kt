package database

import feature.AddNotePayload
import feature.UpdateNotePayload
import kotlinx.coroutines.flow.Flow

interface NoteDao {

    fun getAllNotes(): Flow<List<NoteEntity>>

    suspend fun addNote(addNotePayload: AddNotePayload): Int

    suspend fun updateNote(updateNotePayload: UpdateNotePayload)

    suspend fun updateNoteId(
        localId: Int,
        apiId: Int
    )

    suspend fun updateSyncFailed(noteId: Int, hasSyncFailed: Boolean)

    @Deprecated("Use deleteNotes")
    suspend fun deleteNote(noteId: Int)

    suspend fun deleteNotes(noteIds: List<Int>)

    suspend fun setWasDeleted(noteIds: List<Int>)
}
