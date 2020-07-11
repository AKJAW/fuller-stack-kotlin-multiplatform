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
}
