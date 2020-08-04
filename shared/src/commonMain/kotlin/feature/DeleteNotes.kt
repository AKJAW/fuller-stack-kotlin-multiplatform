package feature

import database.NoteDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import model.NoteIdentifier
import network.NetworkResponse
import network.NoteApi
import network.safeApiCall

class DeleteNotes(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val noteDao: NoteDao,
    private val noteApi: NoteApi
) {

    suspend fun executeAsync(noteIdentifiers: List<NoteIdentifier>): Boolean = withContext(coroutineDispatcher) {
        val ids = noteIdentifiers.map { it.id }
        noteDao.setWasDeleted(ids, true)
        val result = safeApiCall { noteApi.deleteNotes(ids) }

        when (result) {
            is NetworkResponse.Success -> {
                noteDao.deleteNotes(ids)
                true
            }
            else -> false
        }
    }
}
