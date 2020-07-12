package feature.list

import repository.NoteRepository

class RefreshNotes(
    private val noteRepository: NoteRepository
) {
    @Suppress("TooGenericExceptionCaught")
    suspend fun executeAsync() {//TODO failure?
        return try {
            noteRepository.refreshNotes()
        } catch (e: Throwable) { // TODO make more defined
            //TODO
        }
    }
}
