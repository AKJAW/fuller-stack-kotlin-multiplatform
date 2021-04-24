package feature.synchronization

import database.NoteDao
import database.NoteEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import network.NetworkResponse
import network.NoteApi
import network.NoteSchema
import network.safeApiCall

interface SynchronizeNotes {
    sealed class Result {
        object Success : Result()
        object SynchronizationFailed : Result()
    }

    suspend fun executeAsync(): Result

    suspend fun executeAsync(
        localNotes: List<NoteEntity>,
        apiNotes: List<NoteSchema>
    ): Result
}

class SynchronizeApiAndLocalNotes(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val noteDao: NoteDao,
    private val noteApi: NoteApi,
    private val synchronizeDeletedNotes: SynchronizeDeletedNotes,
    private val synchronizeAddedNotes: SynchronizeAddedNotes,
    private val synchronizeUpdatedNotes: SynchronizeUpdatedNotes
) : SynchronizeNotes {

    override suspend fun executeAsync(): SynchronizeNotes.Result = withContext(coroutineDispatcher) {
        val localNotes = noteDao.getAllNotes().firstOrNull()
        if (localNotes == null) {
            return@withContext SynchronizeNotes.Result.SynchronizationFailed
        }

        val networkResponse = safeApiCall { noteApi.getNotes() }
        if (networkResponse !is NetworkResponse.Success) {
            return@withContext SynchronizeNotes.Result.SynchronizationFailed
        }

        val apiNotes = networkResponse.result

        return@withContext executeAsync(localNotes, apiNotes)
    }

    override suspend fun executeAsync(
        localNotes: List<NoteEntity>,
        apiNotes: List<NoteSchema>
    ): SynchronizeNotes.Result = withContext(coroutineDispatcher) {
        synchronizeDeletedNotes.executeAsync(localNotes, apiNotes)
        synchronizeAddedNotes.executeAsync(localNotes, apiNotes)
        synchronizeUpdatedNotes.executeAsync(localNotes, apiNotes)

        return@withContext SynchronizeNotes.Result.Success
    }
}
