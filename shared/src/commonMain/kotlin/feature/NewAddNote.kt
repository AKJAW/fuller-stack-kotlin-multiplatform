package feature

import database.NoteDao
import helpers.date.TimestampProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import network.NetworkResponse
import network.NoteApi
import network.safeApiCall

class NewAddNote(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val noteDao: NoteDao,
    private val noteApi: NoteApi,
    private val timestampProvider: TimestampProvider
) {

    suspend fun executeAsync(title: String, content: String): Boolean = withContext(coroutineDispatcher) {
        val payload = AddNotePayload(title = title, content = content, currentTimestamp = timestampProvider.now())
        val localId = noteDao.addNote(payload)
        val networkResponse = safeApiCall { noteApi.addNote(payload) }

        when (networkResponse) {
            is NetworkResponse.Success -> {
                if(localId != networkResponse.result) {
                    noteDao.updateNoteId(localId, networkResponse.result)
                }
                true
            }
            else -> {
                noteDao.updateSyncFailed(localId, true)
                false
            }
        }
    }
}
