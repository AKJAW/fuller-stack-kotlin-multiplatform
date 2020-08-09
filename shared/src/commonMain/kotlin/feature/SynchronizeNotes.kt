package feature

import database.NoteDao
import database.NoteEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import model.CreationTimestamp
import model.LastModificationTimestamp
import network.NetworkResponse
import network.NoteApi
import network.NoteSchema
import network.safeApiCall

class SynchronizeNotes(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val noteDao: NoteDao,
    private val noteApi: NoteApi
) {

    sealed class Result {
        object Success : Result()
        object SynchronizationFailed : Result()
    }

    suspend fun executeAsync(): Result = withContext(coroutineDispatcher) {
        val localNotes = noteDao.getAllNotes().firstOrNull()
        if(localNotes == null) {
            return@withContext Result.SynchronizationFailed
        }

        val networkResponse = safeApiCall { noteApi.getNotes() }
        if(networkResponse !is NetworkResponse.Success){
            return@withContext Result.SynchronizationFailed
        }

        val apiNotes = networkResponse.result
        checkWasDeleted(localNotes, apiNotes)
        checkHasSyncFailed(localNotes, apiNotes)
        checkIfApiHasNewerNotes(localNotes, apiNotes)

        return@withContext Result.Success
    }

    private suspend fun checkWasDeleted(localNotes: List<NoteEntity>, apiNotes: List<NoteSchema>) {
        val deletedNotes = localNotes.filter { it.wasDeleted }

        if(deletedNotes.isEmpty()) {
            return
        }

        val localNotesToBeRestored = mutableListOf<Int>()
        val localNotesToBeDeleted = mutableListOf<Int>()
        val apiNotesToBeDeleted = mutableListOf<Int>()
        deletedNotes.forEach { localNote ->
            val apiNote = apiNotes.find { apiNote ->
                apiNote.creationTimestamp ==  localNote.creationTimestamp
            }

            if(apiNote == null) {
                localNotesToBeDeleted.add(localNote.noteId)
                return@forEach
            } else if (apiNote.lastModificationTimestamp.unix <= localNote.lastModificationTimestamp.unix) {
                apiNotesToBeDeleted.add(apiNote.apiId)
                localNotesToBeDeleted.add(localNote.noteId)
            } else {
                localNotesToBeRestored.add(localNote.noteId)
            }
        }

        noteDao.deleteNotes(localNotesToBeDeleted)
        safeApiCall { noteApi.deleteNotes(apiNotesToBeDeleted) }
        noteDao.setWasDeleted(localNotesToBeRestored, false)
    }

    private suspend fun checkHasSyncFailed(localNotes: List<NoteEntity>, apiNotes: List<NoteSchema>) {
        val noteWithSyncFailed = localNotes.filter { it.hasSyncFailed }

        noteWithSyncFailed.forEach { localNote ->
            val apiNote = apiNotes.find { apiNote ->
                apiNote.creationTimestamp == localNote.creationTimestamp
            }
            if(apiNote != null){
                handleNoteUpdate(localNote, apiNote)
            } else {
                val payload = AddNotePayload(
                    title = localNote.title,
                    content = localNote.content,
                    lastModificationTimestamp = LastModificationTimestamp(localNote.lastModificationTimestamp.unix),
                    creationTimestamp = CreationTimestamp(localNote.creationTimestamp.unix)
                )
                safeApiCall { noteApi.addNote(payload) }
            }
        }
    }

    private suspend fun handleNoteUpdate(localNote: NoteEntity, apiNote: NoteSchema) {
        val isLocalMoreRecent = localNote.lastModificationTimestamp.unix >= apiNote.lastModificationTimestamp.unix
        if(isLocalMoreRecent) {
            val payload = UpdateNotePayload(
                noteId = apiNote.apiId,
                title = localNote.title,
                content = localNote.title,
                lastModificationTimestamp = localNote.lastModificationTimestamp,
                creationTimestamp = localNote.creationTimestamp
            )
            safeApiCall { noteApi.updateNote(payload) }
        } else {
            val payload = UpdateNotePayload(
                noteId = apiNote.apiId,
                title = apiNote.title,
                content = apiNote.title,
                lastModificationTimestamp = apiNote.lastModificationTimestamp,
                creationTimestamp = apiNote.creationTimestamp
            )
            noteDao.updateNote(payload)
        }
    }

    private suspend fun checkIfApiHasNewerNotes(localNotes: List<NoteEntity>, apiNotes: List<NoteSchema>) {
        if(apiNotes.count() <= localNotes.count()) {
            return
        }

        val newApiNotes = apiNotes.filterNot { apiNote ->
            localNotes.any { localNote -> apiNote.creationTimestamp == localNote.creationTimestamp }
        }

        newApiNotes.forEach { apiNote ->
            val payload = AddNotePayload(
                title = apiNote.title,
                content = apiNote.content,
                lastModificationTimestamp = apiNote.lastModificationTimestamp,
                creationTimestamp = apiNote.creationTimestamp
            )
            noteDao.addNote(payload)
        }
    }
}
