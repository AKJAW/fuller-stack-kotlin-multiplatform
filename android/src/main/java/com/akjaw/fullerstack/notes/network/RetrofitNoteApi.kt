package com.akjaw.fullerstack.notes.network

import com.soywiz.klock.DateTime
import model.Note
import model.schema.NoteSchema
import network.NoteApi

class RetrofitNoteApi(
    private val noteService: NoteService
) : NoteApi {

    override suspend fun getNotes(): List<Note> {
        val schemas = noteService.getNotes()
        return schemas.map { it.toNote() }
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

    private fun NoteSchema.toNote(): Note =
        Note(
            id = this.id,
            title = this.title,
            content = this.content,
            creationDate = DateTime(this.creationDateTimestamp)
        )

}
