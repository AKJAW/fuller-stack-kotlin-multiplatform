package network

import com.soywiz.klock.DateTime
import model.Note
import model.schema.NoteSchema

class NoteSchemaMapper {

    fun toNotes(notes: List<NoteSchema>): List<Note> = notes.map(::toNote)

    fun toNote(note: NoteSchema): Note =
        Note(
            id = note.id,
            title = note.title,
            content = note.content,
            creationDate = DateTime(note.creationDateTimestamp)
        )
}