package feature

import database.NoteDao
import helpers.date.TimestampProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import model.NoteIdentifier
import network.NetworkResponse
import network.NoteApi
import network.safeApiCall

class NewUpdateNote(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val timestampProvider: TimestampProvider,
    private val noteDao: NoteDao,
    private val noteApi: NoteApi
) {

    suspend fun executeAsync(
        noteIdentifier: NoteIdentifier,
        title: String,
        content: String
    ): Boolean = withContext(coroutineDispatcher) {
        noteDao.updateNote(
            noteId = noteIdentifier.id,
            title = title,
            content = content,
            lastModificationTimestamp = timestampProvider.now()
        )

        val payload = UpdateNotePayload(
            noteIdentifier = noteIdentifier,
            title = title,
            content = content,
            lastModificationTimestamp = timestampProvider.now()
        )
        val networkResponse = safeApiCall { noteApi.updateNote(payload) }

        when(networkResponse) {
            is NetworkResponse.Success -> true
            else -> {
                noteDao.updateSyncFailed(noteIdentifier.id, true)
                false
            }
        }
    }
}
