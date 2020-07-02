package network

import data.Note

interface NoteApi {
    suspend fun getNotes(): List<Note>
}
