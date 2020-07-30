package network

import feature.UpdateNotePayload
import model.Note
import model.NoteIdentifier
import model.schema.NoteSchema

interface NoteApi {

    suspend fun getNotes(): List<Note>

    suspend fun addNote(newNote: NoteSchema): Int

    suspend fun updateNote(updatedNotePayload: UpdateNotePayload)

    suspend fun deleteNotes(noteIdentifiers: List<NoteIdentifier>)
}
