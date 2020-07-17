package feature.list

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import network.NetworkResponse
import network.safeApiCall
import repository.NoteRepository

class RefreshNotes(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val noteRepository: NoteRepository
) {
    @Suppress("TooGenericExceptionCaught")
    suspend fun executeAsync(): Boolean = withContext(coroutineDispatcher) {//TODO failure?

        val result = safeApiCall { noteRepository.refreshNotes() }

        when (result) {
            is NetworkResponse.Success -> true
            else -> false
        }
    }
}
