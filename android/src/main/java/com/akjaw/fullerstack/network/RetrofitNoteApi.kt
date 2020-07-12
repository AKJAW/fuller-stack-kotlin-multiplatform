package com.akjaw.fullerstack.network

import com.soywiz.klock.DateTime
import model.Note
import model.schema.NoteSchema
import network.NoteApi

class RetrofitNoteApi(private val noteService: NoteService) : NoteApi {

    override suspend fun getNotes(): List<Note> = noteService.getNotes().toNotes()

    private fun List<NoteSchema>.toNotes(): List<Note> {
        return this.map { schema ->
            Note(
                id = schema.id,
                title = schema.title,
                content = schema.content,
                creationDate = DateTime(schema.creationDateTimestamp)
            )
        }
    }
}
