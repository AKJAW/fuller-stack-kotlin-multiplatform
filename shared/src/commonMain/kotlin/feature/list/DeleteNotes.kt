package feature.list

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import network.NetworkResponse
import network.safeApiCall
import repository.NoteRepository

class DeleteNotes(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val noteRepository: NoteRepository
) {

    suspend fun executeAsync(noteIds: List<Int>): Boolean = withContext(coroutineDispatcher) {
        val result = safeApiCall { noteRepository.deleteNotes(noteIds) }

        when (result) {
            is NetworkResponse.Success -> true
            else -> false
        }
    }
}
