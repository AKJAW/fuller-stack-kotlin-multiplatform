package tests

import database.NoteDao
import database.NoteEntity
import feature.AddNotePayload
import feature.UpdateNotePayload
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import model.CreationTimestamp
import model.Note

class NoteDaoTestFake : NoteDao {
    val notesMutableState: MutableStateFlow<List<NoteEntity>> = MutableStateFlow(listOf())
    var notes: List<NoteEntity>
        get() = notesMutableState.value
        set(value) {
            notesMutableState.value = value
        }

    fun initializeNoteEntities(notes: List<Note>) {
        this.notes = notes.map { note ->
            NoteEntity(
                id = note.noteIdentifier.id,
                noteId = note.noteIdentifier.id,
                title = note.title,
                content = note.content,
                lastModificationTimestamp = note.lastModificationTimestamp,
                creationTimestamp = note.creationTimestamp,
                hasSyncFailed = false,
                wasDeleted = false
            )
        }
    }

    override fun getAllNotes(): Flow<List<NoteEntity>> = notesMutableState

    override suspend fun addNote(addNotePayload: AddNotePayload): Int {
        val latestId = notes.maxBy { it.id }?.id ?: -1
        val newId = latestId + 1

        val newNote = NoteEntity(
            id = newId,
            noteId = newId,
            title = addNotePayload.title,
            content = addNotePayload.content,
            lastModificationTimestamp = addNotePayload.lastModificationTimestamp,
            creationTimestamp = addNotePayload.creationTimestamp
        )
        notes = notes + newNote

        return newId
    }

    override suspend fun updateNote(updateNotePayload: UpdateNotePayload) {
        val noteToUpdate = notes.first { it.creationTimestamp == updateNotePayload.creationTimestamp }
        val updatedNote = noteToUpdate.copy(
            title = updateNotePayload.title,
            content = updateNotePayload.content,
            lastModificationTimestamp = updateNotePayload.lastModificationTimestamp
        )
        notes = notes.map { note -> //TODO replace with mutable list
            if(note.creationTimestamp == updateNotePayload.creationTimestamp) {
                updatedNote
            } else {
                note
            }
        }
    }

    override suspend fun updateNoteId(localId: Int, apiId: Int) {
        val newNotes = notes.map { note ->//TODO replace with mutable list
            if(note.id == localId) {
                note.copy(noteId = apiId)
            } else {
                note
            }
        }
        notes = newNotes
    }

    override suspend fun updateSyncFailed(creationTimestamp: CreationTimestamp, hasSyncFailed: Boolean) {
        val newNotes = notes.map { note ->//TODO replace with mutable list
            if(note.creationTimestamp == creationTimestamp) {
                note.copy(hasSyncFailed = hasSyncFailed)
            } else {
                note
            }
        }
        notes = newNotes
    }

    override suspend fun deleteNotes(noteIds: List<Int>) {
        val newNotes = notes.filterNot { noteIds.contains(it.noteId) }
        notes = newNotes
    }

    override suspend fun setWasDeleted(noteIds: List<Int>, wasDeleted: Boolean) {
        val newNotes = notes.map { note ->
            if(noteIds.contains(note.noteId)){
                note.copy(wasDeleted = wasDeleted)
            } else {
                note
            }
        }
        notes = newNotes
    }
}
