package network

import model.Note
import model.NoteIdentifier
import model.schema.NoteSchema

interface NoteApi {

    suspend fun getNotes(): List<Note>

    suspend fun addNote(newNote: NoteSchema): Int

    suspend fun updateNote(updatedNote: Note)

    suspend fun deleteNotes(noteIdentifiers: List<NoteIdentifier>)
}
