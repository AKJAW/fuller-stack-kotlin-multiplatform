package database

import feature.AddNotePayload
import feature.UpdateNotePayload
import kotlinx.coroutines.flow.Flow
import model.CreationTimestamp

interface NoteDao {

    fun getAllNotes(): Flow<List<NoteEntity>>

    suspend fun addNote(addNotePayload: AddNotePayload): Int

    suspend fun updateNote(updateNotePayload: UpdateNotePayload)

    suspend fun updateSyncFailed(creationTimestamp: CreationTimestamp, hasSyncFailed: Boolean)

    suspend fun deleteNotes(noteIds: List<Int>)

    suspend fun setWasDeleted(noteIds: List<Int>, wasDeleted: Boolean)
}
