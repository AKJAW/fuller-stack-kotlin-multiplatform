package tests

import model.Note
import model.NoteIdentifier
import model.schema.NoteSchema
import network.NoteApi

@Suppress("TooGenericExceptionThrown")
class NoteApiTestFake : NoteApi {
    val notes = mutableListOf<NoteSchema>()
    var willFail = false

    override suspend fun getNotes(): List<Note> {
        TODO("Not yet implemented")
    }

    override suspend fun addNote(newNote: NoteSchema): Int {
        if (willFail) throw Throwable()
        val lastNoteId = notes.maxBy { it.apiId }?.apiId ?: -1
        val newNoteId = lastNoteId + 1

        val noteWithId = newNote.copy(apiId = newNoteId)
        notes.add(noteWithId)

        return newNoteId
    }

    override suspend fun updateNote(updatedNote: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNotes(noteIdentifiers: List<NoteIdentifier>) {
        TODO("Not yet implemented")
    }
}
