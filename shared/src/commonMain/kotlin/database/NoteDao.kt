package database

import feature.AddNotePayload
import feature.UpdateNotePayload
import kotlinx.coroutines.flow.Flow
import model.CreationTimestamp

interface NoteDao {

    suspend fun getAllNotes(): Flow<List<NoteEntity>>

    suspend fun addNote(addNotePayload: AddNotePayload): Int

    suspend fun updateNote(updateNotePayload: UpdateNotePayload)

    suspend fun updateSyncFailed(creationTimestamp: CreationTimestamp, hasSyncFailed: Boolean)

    suspend fun deleteNotes(creationTimestamps: List<CreationTimestamp>)

    suspend fun setWasDeleted(creationTimestamps: List<CreationTimestamp>, wasDeleted: Boolean)
}
