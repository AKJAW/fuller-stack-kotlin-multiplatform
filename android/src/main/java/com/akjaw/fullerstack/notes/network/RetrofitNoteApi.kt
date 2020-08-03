package com.akjaw.fullerstack.notes.network

import feature.AddNotePayload
import feature.UpdateNotePayload
import model.Note
import model.NoteIdentifier
import network.NoteApi
import network.NoteSchemaMapper

class RetrofitNoteApi(
    private val noteService: NoteService,
    private val noteSchemaMapper: NoteSchemaMapper
) : NoteApi {

    override suspend fun getNotes(): List<Note> {
        val schemas = noteService.getNotes()
        return noteSchemaMapper.toNotes(schemas)
    }

    override suspend fun addNote(addNotePayload: AddNotePayload): Int {
        return noteService.addNote(addNotePayload)
    }

    override suspend fun updateNote(updatedNotePayload: UpdateNotePayload) {
        noteService.updateNote(updatedNotePayload)
    }

    override suspend fun deleteNotes(noteIdentifiers: List<NoteIdentifier>) {
        val ids = noteIdentifiers.map { it.id }
        if (ids.count() == 1) {
            noteService.deleteNote(ids.first())
        } else {
            noteService.deleteNotes(ids)
        }
    }
}
