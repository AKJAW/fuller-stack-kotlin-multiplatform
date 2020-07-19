package feature.list

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import model.NoteIdentifier
import network.NetworkResponse
import network.safeApiCall
import repository.NoteRepository

class DeleteNotes(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val noteRepository: NoteRepository
) {

    suspend fun executeAsync(noteIdentifiers: List<NoteIdentifier>): Boolean = withContext(coroutineDispatcher) {
        val result = safeApiCall { noteRepository.deleteNotes(noteIdentifiers) }

        when (result) {
            is NetworkResponse.Success -> true
            else -> false
        }
    }
}
