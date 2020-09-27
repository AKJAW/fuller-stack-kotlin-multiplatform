package feature.synchronization

import database.NoteDao
import database.NoteEntity
import feature.UpdateNotePayload
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import network.NetworkResponse
import network.NoteApi
import network.NoteSchema
import network.safeApiCall

class SynchronizeUpdatedNotes(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val noteDao: NoteDao,
    private val noteApi: NoteApi
) {

    suspend fun executeAsync(
        localNotes: List<NoteEntity>,
        apiNotes: List<NoteSchema>
    ) = withContext(coroutineDispatcher) {
        localNotes.forEach { localNote ->
            val apiNote = apiNotes.firstOrNull { apiNote ->
                apiNote.creationTimestamp == localNote.creationTimestamp
            }

            if (apiNote != null && apiNote.lastModificationTimestamp != localNote.lastModificationTimestamp) {
                handleNoteUpdate(localNote, apiNote)
            }
        }
    }

    private suspend fun handleNoteUpdate(localNote: NoteEntity, apiNote: NoteSchema) {
        val isLocalMoreRecent = localNote.lastModificationTimestamp.unix >= apiNote.lastModificationTimestamp.unix
        if (isLocalMoreRecent) {
            val payload = UpdateNotePayload(
                title = localNote.title,
                content = localNote.title,
                lastModificationTimestamp = localNote.lastModificationTimestamp,
                creationTimestamp = localNote.creationTimestamp
            )

            val response = safeApiCall { noteApi.updateNote(payload) }
            if (response is NetworkResponse.Success) {
                noteDao.updateSyncFailed(payload.creationTimestamp, false)
            }
        } else {
            val payload = UpdateNotePayload(
                title = apiNote.title,
                content = apiNote.title,
                lastModificationTimestamp = apiNote.lastModificationTimestamp,
                creationTimestamp = apiNote.creationTimestamp
            )
            noteDao.updateNote(payload)
        }
    }
}
