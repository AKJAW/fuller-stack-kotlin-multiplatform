package feature

import database.NoteDao
import helpers.date.UnixTimestampProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import model.CreationTimestamp
import model.toLastModificationTimestamp
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
        creationTimestamp: CreationTimestamp,
        title: String,
        content: String
    ): Boolean = withContext(coroutineDispatcher) {

        val unixTimestamp = unixTimestampProvider.now()
        val payload = UpdateNotePayload(
            title = title,
            content = content,
            lastModificationTimestamp = unixTimestamp.toLastModificationTimestamp(),
            creationTimestamp = creationTimestamp
        )
        noteDao.updateNote(payload)
        val networkResponse = safeApiCall { noteApi.updateNote(payload) }

        when(networkResponse) {
            is NetworkResponse.Success -> true
            else -> {
                noteDao.updateSyncFailed(payload.creationTimestamp, true)
                false
            }
        }
    }
}
