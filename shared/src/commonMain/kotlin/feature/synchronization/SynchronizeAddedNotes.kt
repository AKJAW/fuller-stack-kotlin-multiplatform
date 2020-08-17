package feature.synchronization

import database.NoteDao
import database.NoteEntity
import feature.AddNotePayload
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import model.toCreationTimestamp
import model.toLastModificationTimestamp
import network.NoteApi
import network.NoteSchema
import network.safeApiCall

class SynchronizeAddedNotes(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val noteDao: NoteDao,
    private val noteApi: NoteApi
) {

    suspend fun executeAsync(
        localNotes: List<NoteEntity>,
        apiNotes: List<NoteSchema>
    ) = withContext(coroutineDispatcher) {
        val newLocalNotes = localNotes.filter { localNote ->
            apiNotes.none { apiNote -> apiNote.creationTimestamp == localNote.creationTimestamp }
        }
        val newApiNotes = apiNotes.filter { apiNote ->
            localNotes.none { localNote -> localNote.creationTimestamp == apiNote.creationTimestamp }
        }

        newLocalNotes.forEach { localNote ->
            val payload = AddNotePayload(
                title = localNote.title,
                content = localNote.content,
                lastModificationTimestamp = localNote.lastModificationTimestamp.unix.toLastModificationTimestamp(),
                creationTimestamp = localNote.creationTimestamp.unix.toCreationTimestamp()
            )
            safeApiCall { noteApi.addNote(payload) }
        }

        newApiNotes.forEach { apiNote ->
            val payload = AddNotePayload(
                title = apiNote.title,
                content = apiNote.content,
                lastModificationTimestamp = apiNote.lastModificationTimestamp.unix.toLastModificationTimestamp(),
                creationTimestamp = apiNote.creationTimestamp.unix.toCreationTimestamp()
            )
            noteDao.addNote(payload)
        }
    }
}
