package feature

import database.NoteDao
import helpers.date.UnixTimestampProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import model.LastModificationTimestamp
import model.NoteIdentifier
import network.NetworkResponse
import network.NoteApi
import network.safeApiCall

class UpdateNote(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val unixTimestampProvider: UnixTimestampProvider,
    private val noteDao: NoteDao,
    private val noteApi: NoteApi
) {

    suspend fun executeAsync(
        noteIdentifier: NoteIdentifier,
        title: String,
        content: String
    ): Boolean = withContext(coroutineDispatcher) {

        val unixTimestamp = unixTimestampProvider.now()
        val payload = UpdateNotePayload(
            noteId = noteIdentifier.id,
            title = title,
            content = content,
            lastModificationTimestamp = LastModificationTimestamp(unixTimestamp),
            creationTimestamp = TODO()
        )
        noteDao.updateNote(payload)
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
