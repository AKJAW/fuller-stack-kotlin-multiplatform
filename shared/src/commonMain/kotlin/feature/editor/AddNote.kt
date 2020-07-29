package feature.editor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import model.Note
import network.NetworkResponse
import network.safeApiCall
import repository.NoteRepository

@Deprecated("Use the new add note")
class AddNote(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val noteRepository: NoteRepository
) {

    suspend fun executeAsync(note: Note): Boolean = withContext(coroutineDispatcher) {
        val result = safeApiCall { noteRepository.addNote(note) }

        when (result) {
            is NetworkResponse.Success -> true
            else -> false
        }
    }
}
