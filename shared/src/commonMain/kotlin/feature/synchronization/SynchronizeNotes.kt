package feature.synchronization

import database.NoteDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import network.NetworkResponse
import network.NoteApi
import network.safeApiCall

class SynchronizeNotes(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val noteDao: NoteDao,
    private val noteApi: NoteApi,
    private val synchronizeDeletedNotes: SynchronizeDeletedNotes,
    private val synchronizeAddedNotes: SynchronizeAddedNotes,
    private val synchronizeUpdatedNotes: SynchronizeUpdatedNotes
) {

    sealed class Result {
        object Success : Result()
        object SynchronizationFailed : Result()
    }

    suspend fun executeAsync(): Result = withContext(coroutineDispatcher) {
        val localNotes = noteDao.getAllNotes().firstOrNull()
        if (localNotes == null) {
            return@withContext Result.SynchronizationFailed
        }

        val networkResponse = safeApiCall { noteApi.getNotes() }
        if (networkResponse !is NetworkResponse.Success) {
            return@withContext Result.SynchronizationFailed
        }

        val apiNotes = networkResponse.result
        synchronizeDeletedNotes.executeAsync(localNotes, apiNotes)
        synchronizeAddedNotes.executeAsync(localNotes, apiNotes)
        synchronizeUpdatedNotes.executeAsync(localNotes, apiNotes)

        return@withContext Result.Success
    }
}
