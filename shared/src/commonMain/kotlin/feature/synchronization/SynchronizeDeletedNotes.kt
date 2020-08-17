package feature.synchronization

import database.NoteDao
import database.NoteEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import model.CreationTimestamp
import network.NoteApi
import network.NoteSchema
import network.safeApiCall

//TODO add synchronization of API deleted notes
class SynchronizeDeletedNotes(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val noteDao: NoteDao,
    private val noteApi: NoteApi
) {

    suspend fun executeAsync(
        localNotes: List<NoteEntity>,
        apiNotes: List<NoteSchema>
    ) = withContext(coroutineDispatcher) {
        val deletedNotes = localNotes.filter { it.wasDeleted }

        if(deletedNotes.isEmpty()) {
            return@withContext
        }

        val localNotesToBeRestored = mutableListOf<CreationTimestamp>()
        val localNotesToBeDeleted = mutableListOf<CreationTimestamp>()
        val apiNotesToBeDeleted = mutableListOf<CreationTimestamp>()
        deletedNotes.forEach { localNote ->
            val apiNote = apiNotes.find { apiNote ->
                apiNote.creationTimestamp ==  localNote.creationTimestamp
            }

            if(apiNote == null) {
                localNotesToBeDeleted.add(localNote.creationTimestamp)
                return@forEach
            } else if (apiNote.lastModificationTimestamp.unix <= localNote.lastModificationTimestamp.unix) {
                apiNotesToBeDeleted.add(apiNote.creationTimestamp)
                localNotesToBeDeleted.add(localNote.creationTimestamp)
            } else {
                localNotesToBeRestored.add(localNote.creationTimestamp)
            }
        }

        noteDao.deleteNotes(localNotesToBeDeleted)
        safeApiCall { noteApi.deleteNotes(apiNotesToBeDeleted) }
        noteDao.setWasDeleted(localNotesToBeRestored, false)
    }
}
