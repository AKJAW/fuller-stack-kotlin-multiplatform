package com.akjaw.fullerstack.notes.network

import model.Note
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

    override suspend fun addNote(newNote: Note) {
        val noteRequest = AddNoteRequest(
            title = newNote.title,
            content = newNote.content
        )
        noteService.addNote(noteRequest)
    }

    override suspend fun updateNote(updatedNote: Note) {
        val noteRequest = UpdateNoteRequest(
            id = updatedNote.id,
            title = updatedNote.title,
            content = updatedNote.content
        )
        noteService.updateNote(noteRequest)
    }
}
