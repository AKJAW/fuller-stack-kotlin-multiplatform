package tests

import model.Note
import model.NoteIdentifier
import network.NoteApi

@Suppress("TooGenericExceptionThrown")
class NetworkApiTestFake : NoteApi {
    var notes: List<Note> = listOf()
    var willFail = false

    override suspend fun getNotes(): List<Note> {
        if (willFail) {
            throw Exception() // TODO make more defined
        }
        return notes
    }

    override suspend fun addNote(newNote: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun updateNote(updatedNote: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNotes(noteIdentifiers: List<NoteIdentifier>) {
        TODO("Not yet implemented")
    }
}
