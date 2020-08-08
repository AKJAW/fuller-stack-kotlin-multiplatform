package feature

import database.NoteDao
import helpers.date.UnixTimestampProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import model.CreationTimestamp
import model.LastModificationTimestamp
import network.NetworkResponse
import network.NoteApi
import network.safeApiCall

class AddNote(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val noteDao: NoteDao,
    private val noteApi: NoteApi,
    private val unixTimestampProvider: UnixTimestampProvider
) {

    suspend fun executeAsync(title: String, content: String): Boolean = withContext(coroutineDispatcher) {
        val currentUnixTimestamp = unixTimestampProvider.now()
        val payload = AddNotePayload(
            title = title,
            content = content,
            lastModificationTimestamp = LastModificationTimestamp(currentUnixTimestamp),
            creationTimestamp = CreationTimestamp(currentUnixTimestamp)
        )
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
