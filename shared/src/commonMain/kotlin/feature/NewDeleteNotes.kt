package feature

import database.NoteDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import model.NoteIdentifier
import network.NetworkResponse
import network.NoteApi
import network.safeApiCall

class NewDeleteNotes(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val noteDao: NoteDao,
    private val noteApi: NoteApi
) {

    suspend fun executeAsync(noteIdentifiers: List<NoteIdentifier>): Boolean = withContext(coroutineDispatcher) {
        val ids = noteIdentifiers.map { it.id }
        noteDao.setWasDeleted(ids)
        val result = safeApiCall { noteApi.deleteNotes(noteIdentifiers) }

        when (result) {
            is NetworkResponse.Success -> {
                noteDao.deleteNotes(ids)
                true
            }
            else -> false
        }
    }
}
