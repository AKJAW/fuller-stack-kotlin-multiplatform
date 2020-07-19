package tests

import model.Note
import network.NoteApi

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

    override suspend fun deleteNotes(noteIds: List<Int>) {
        TODO("Not yet implemented")
    }
}
