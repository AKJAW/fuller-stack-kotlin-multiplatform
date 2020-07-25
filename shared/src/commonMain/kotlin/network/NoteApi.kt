package network

import model.Note
import model.NoteIdentifier

interface NoteApi {

    suspend fun getNotes(): List<Note>

    suspend fun addNote(newNote: Note)

    suspend fun updateNote(updatedNote: Note)

    suspend fun deleteNotes(noteIdentifiers: List<NoteIdentifier>)
}
