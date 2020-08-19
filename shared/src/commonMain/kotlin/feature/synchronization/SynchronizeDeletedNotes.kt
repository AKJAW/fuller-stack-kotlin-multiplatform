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
        val deletedLocalNotes = localNotes.filter { it.wasDeleted }
        val deletedApiNotes = apiNotes.filter { it.wasDeleted }

        if(deletedLocalNotes.isEmpty() && deletedApiNotes.isEmpty()) {
            return@withContext
        }

        val localNotesToBeRestored = mutableSetOf<CreationTimestamp>()
        val localNotesToBeDeleted = mutableSetOf<CreationTimestamp>()
        val apiNotesToBeRestored = mutableSetOf<CreationTimestamp>()
        val apiNotesToBeDeleted = mutableSetOf<CreationTimestamp>()

        deletedApiNotes.forEach { apiNote ->
            val localNote = localNotes.find { localNote ->
                apiNote.creationTimestamp ==  localNote.creationTimestamp
            }

            if(localNote == null) return@forEach

            if(apiNote.lastModificationTimestamp.unix >= localNote.lastModificationTimestamp.unix) {
                localNotesToBeDeleted.add(localNote.creationTimestamp)
            } else {
                apiNotesToBeRestored.add(localNote.creationTimestamp)
            }
        }

        deletedLocalNotes.forEach { localNote ->
            val apiNote = apiNotes.find { apiNote ->
                apiNote.creationTimestamp ==  localNote.creationTimestamp
            }

            when {
                apiNote == null -> localNotesToBeDeleted.add(localNote.creationTimestamp)
                apiNote.lastModificationTimestamp.unix <= localNote.lastModificationTimestamp.unix -> {
                    apiNotesToBeDeleted.add(localNote.creationTimestamp)
                    localNotesToBeDeleted.add(localNote.creationTimestamp)
                }
                else -> localNotesToBeRestored.add(localNote.creationTimestamp)
            }
        }

        if( localNotesToBeDeleted.count() > 0) noteDao.deleteNotes(localNotesToBeDeleted.toList())
        if (apiNotesToBeDeleted.count() > 0) safeApiCall { noteApi.deleteNotes(apiNotesToBeDeleted.toList()) }
        if (localNotesToBeRestored.count() > 0) noteDao.setWasDeleted(localNotesToBeRestored.toList(), false)
        if (apiNotesToBeRestored.count() > 0) safeApiCall { noteApi.restoreNotes(apiNotesToBeRestored.toList()) }
    }
}
