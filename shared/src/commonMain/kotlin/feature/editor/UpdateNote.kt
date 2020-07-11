package feature.editor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import model.Note
import repository.NoteRepository

class UpdateNote(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val noteRepository: NoteRepository
) {

    @Suppress("TooGenericExceptionCaught")
    suspend fun executeAsync(note: Note): Boolean = withContext(coroutineDispatcher) {
        try {
            noteRepository.updateNote(note)
            true
        } catch (e: Exception) {//TODO make more defined
            false
        }
    }
}
