package feature.list

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import repository.NoteRepository

class RefreshNotes(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val noteRepository: NoteRepository
) {
    @Suppress("TooGenericExceptionCaught")
    suspend fun executeAsync() = withContext(coroutineDispatcher) {//TODO failure?
        try {
            noteRepository.refreshNotes()
        } catch (e: Throwable) { // TODO make more defined
            //TODO
        }
    }
}
