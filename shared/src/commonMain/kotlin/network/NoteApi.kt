package network

import feature.AddNotePayload
import feature.UpdateNotePayload
import model.Note
import model.NoteIdentifier

interface NoteApi {

    suspend fun getNotes(): List<Note>

    suspend fun addNote(addNotePayload: AddNotePayload): Int

    suspend fun updateNote(updatedNotePayload: UpdateNotePayload)

    suspend fun deleteNotes(noteIdentifiers: List<NoteIdentifier>)
}
