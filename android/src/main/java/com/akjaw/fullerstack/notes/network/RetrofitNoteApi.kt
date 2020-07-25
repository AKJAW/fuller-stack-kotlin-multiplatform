package com.akjaw.fullerstack.notes.network

import model.Note
import model.NoteIdentifier
import model.schema.NoteRequest
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
        val noteRequest = NoteRequest(
            title = newNote.title,
            content = newNote.content
        )
        noteService.addNote(noteRequest)
    }

    override suspend fun updateNote(updatedNote: Note) {
        val noteRequest = NoteRequest(
            title = updatedNote.title,
            content = updatedNote.content
        )
        noteService.updateNote(updatedNote.noteIdentifier.id, noteRequest)
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
