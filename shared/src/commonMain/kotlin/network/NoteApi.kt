package network

import model.Note

interface NoteApi {

    suspend fun getNotes(): List<Note>

    suspend fun addNote(newNote: Note)

    suspend fun updateNote(updatedNote: Note)

    suspend fun deleteNotes(noteIds: List<Int>)
}
