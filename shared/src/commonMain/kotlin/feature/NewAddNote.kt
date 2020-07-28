package feature

import database.NoteDao
import database.NoteEntityMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import model.Note
import network.NetworkResponse
import network.NoteApi
import network.NoteSchemaMapper
import network.safeApiCall

class NewAddNote(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val noteEntityMapper: NoteEntityMapper,
    private val noteDao: NoteDao,
    private val noteSchemaMapper: NoteSchemaMapper,
    private val noteApi: NoteApi
) {

    suspend fun executeAsync(note: Note): Boolean = withContext(coroutineDispatcher) {
        val localId = addToLocalDatabase(note)
        val networkResponse = addToApi(note)

        when (networkResponse) {
            is NetworkResponse.Success -> {
                noteDao.updateId(localId, networkResponse.result)
                true
            }
            else -> {
                noteDao.updateSyncFailed(localId, true)
                false
            }
        }
    }

    private suspend fun addToLocalDatabase(note: Note): Int {
        val entity = noteEntityMapper.toEntity(note)
        return noteDao.addNote(entity)
    }

    private suspend fun addToApi(note: Note): NetworkResponse<Int> {
        val schema = noteSchemaMapper.toSchema(note)
        return safeApiCall { noteApi.addNote(schema) }
    }
}
