package tests

import database.NoteDao
import database.NoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class NoteDaoTestFake : NoteDao {
    private val notesMutableState: MutableStateFlow<List<NoteEntity>> = MutableStateFlow(listOf())
    var notes: List<NoteEntity>
        get() = notesMutableState.value
        set(value) {
            notesMutableState.value = value
        }

    override fun getAllNotes(): Flow<List<NoteEntity>> = notesMutableState

    override suspend fun addNote(note: NoteEntity): Int {
        val latestId = notes.maxBy { it.id }?.id ?: -1
        val newId = latestId + 1

        val newNote = note.copy(id = newId, noteId = newId)
        notes = notes + newNote

        return newId
    }

    override suspend fun updateNote(noteId: Int, title: String, content: String, lastModificationTimestamp: Long) {
        val noteToUpdate = notes.first { it.noteId == noteId }
        val updatedNote = noteToUpdate.copy(
            title = title,
            content = content,
            lastModificationTimestamp = lastModificationTimestamp
        )
        notes = notes.map { note -> //TODO replace with mutable list
            if(note.noteId == noteId) {
                updatedNote
            } else {
                note
            }
        }
    }

    override suspend fun updateId(localId: Int, apiId: Int) {
        val newNotes = notes.map { note ->//TODO replace with mutable list
            if(note.id == localId) {
                note.copy(noteId = apiId)
            } else {
                note
            }
        }
        notes = newNotes
    }

    override suspend fun updateSyncFailed(noteId: Int, hasSyncFailed: Boolean) {
        val newNotes = notes.map { note ->//TODO replace with mutable list
            if(note.noteId == noteId) {
                note.copy(hasSyncFailed = hasSyncFailed)
            } else {
                note
            }
        }
        notes = newNotes
    }

    override suspend fun deleteNote(noteId: Int) {
        val newNotes = notes.filterNot { it.noteId == noteId }
        notes = newNotes
    }

    override suspend fun deleteNotes(noteIds: List<Int>) {
        val newNotes = notes.filterNot { noteIds.contains(it.noteId) }
        notes = newNotes
    }

    override suspend fun setWasDeleted(noteIds: List<Int>) {
        val newNotes = notes.map { note ->
            if(noteIds.contains(note.noteId)){
                note.copy(wasDeleted = true)
            } else {
                note
            }
        }
        notes = newNotes
    }
}
