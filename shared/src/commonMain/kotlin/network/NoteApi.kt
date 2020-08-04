package network

import feature.AddNotePayload
import feature.UpdateNotePayload
import model.schema.NoteSchema

interface NoteApi {

    suspend fun getNotes(): List<NoteSchema>

    suspend fun addNote(addNotePayload: AddNotePayload): Int

    suspend fun updateNote(updatedNotePayload: UpdateNotePayload)

    suspend fun deleteNotes(ids: List<Int>)
}
