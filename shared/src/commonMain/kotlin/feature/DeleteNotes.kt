package feature

import database.NoteDao
import helpers.date.UnixTimestampProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import model.CreationTimestamp
import network.NetworkResponse
import network.NoteApi
import network.safeApiCall

class DeleteNotes(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val timestampProvider: UnixTimestampProvider,
    private val noteDao: NoteDao,
    private val noteApi: NoteApi
) {

    suspend fun executeAsync(creationTimestamps: List<CreationTimestamp>): Boolean = withContext(coroutineDispatcher) {
        val timestamp = timestampProvider.now()
        noteDao.setWasDeleted(creationTimestamps, true, timestamp)
        val result = safeApiCall { noteApi.deleteNotes(creationTimestamps) }

        when (result) {
            is NetworkResponse.Success -> {
                noteDao.deleteNotes(creationTimestamps)
                true
            }
            else -> false
        }
    }
}
