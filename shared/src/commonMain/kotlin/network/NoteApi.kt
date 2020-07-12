package network

import model.Note

interface NoteApi {

    suspend fun getNotes(): List<Note>
}
