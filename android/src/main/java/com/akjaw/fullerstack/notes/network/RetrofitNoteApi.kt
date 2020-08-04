package com.akjaw.fullerstack.notes.network

import feature.AddNotePayload
import feature.UpdateNotePayload
import model.schema.NoteSchema
import network.NoteApi

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

    override suspend fun deleteNotes(ids: List<Int>) {
        noteService.deleteNotes(ids)
    }
}
