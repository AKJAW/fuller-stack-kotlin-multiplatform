package com.akjaw.fullerstack.notes.network

import feature.AddNotePayload
import feature.DeleteNotePayload
import feature.UpdateNotePayload
import model.CreationTimestamp
import network.NoteApi
import network.NoteSchema

class RetrofitNoteApi(
    private val noteService: NoteService
) : NoteApi {

    override suspend fun getNotes(): List<NoteSchema> {
        return noteService.getNotes()
    }

    override suspend fun addNote(addNotePayload: AddNotePayload): Int {
        return noteService.addNote(addNotePayload)
    }

    override suspend fun updateNote(updatedNotePayload: UpdateNotePayload) {
        noteService.updateNote(updatedNotePayload)
    }

    override suspend fun deleteNotes(creationTimestamps: List<CreationTimestamp>) {
        val payloads = creationTimestamps.createDeleteNotePayloads(wasDeleted = true)
        noteService.deleteNotes(payloads)
    }

    override suspend fun restoreNotes(creationTimestamps: List<CreationTimestamp>) {
        val payloads = creationTimestamps.createDeleteNotePayloads(wasDeleted = false)
        noteService.deleteNotes(payloads)
    }

    private fun List<CreationTimestamp>.createDeleteNotePayloads(wasDeleted: Boolean): List<DeleteNotePayload> {
        return this.map { creationTimestamp ->
            DeleteNotePayload(
                creationTimestamp = creationTimestamp,
                wasDeleted = wasDeleted
            )
        }
    }
}
