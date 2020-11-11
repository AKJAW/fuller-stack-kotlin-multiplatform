package tests

import database.NoteDao
import database.NoteEntity
import feature.AddNotePayload
import feature.UpdateNotePayload
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import model.CreationTimestamp
import model.Note
import model.toLastModificationTimestamp

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
                localId = -1,
                title = note.title,
                content = note.content,
                lastModificationTimestamp = note.lastModificationTimestamp,
                creationTimestamp = note.creationTimestamp,
                hasSyncFailed = note.hasSyncFailed,
                wasDeleted = false
            )
        }
    }

    override fun getAllNotes(): Flow<List<NoteEntity>> = notesMutableState

    override suspend fun addNote(addNotePayload: AddNotePayload): Int {
        val latestId = notes.maxByOrNull { it.localId }?.localId ?: -1
        val newId = latestId + 1

        val newNote = NoteEntity(
            localId = newId,
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
        notes = notes.map { note ->
            if (note.creationTimestamp == updateNotePayload.creationTimestamp) {
                updatedNote
            } else {
                note
            }
        }
    }

    override suspend fun updateSyncFailed(creationTimestamp: CreationTimestamp, hasSyncFailed: Boolean) {
        val newNotes = notes.map { note ->
            if (note.creationTimestamp == creationTimestamp) {
                note.copy(hasSyncFailed = hasSyncFailed)
            } else {
                note
            }
        }
        notes = newNotes
    }

    override suspend fun deleteNotes(creationTimestamps: List<CreationTimestamp>) {
        val newNotes = notes.filterNot { creationTimestamps.contains(it.creationTimestamp) }
        notes = newNotes
    }

    override suspend fun setWasDeleted(
        creationTimestamps: List<CreationTimestamp>,
        wasDeleted: Boolean,
        lastModificationTimestamp: Long
    ) {
        val newNotes = notes.map { note ->
            if (creationTimestamps.contains(note.creationTimestamp)) {
                note.copy(
                    wasDeleted = wasDeleted,
                    lastModificationTimestamp = lastModificationTimestamp.toLastModificationTimestamp()
                )
            } else {
                note
            }
        }
        notes = newNotes
    }
}
