package network

import data.Note

class NetworkApiFake : NoteApi {
    var callCount = 0
    var notes: List<Note> = listOf()
    var willFail = false

    override suspend fun getNotes(): List<Note> {
        callCount++
        if (willFail) {
            throw Error() // TODO make more defined
        }
        return notes
    }
}
