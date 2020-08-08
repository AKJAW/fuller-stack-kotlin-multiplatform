package network

import model.Note
import model.NoteIdentifier

class NoteSchemaMapper {

    fun toNotes(notes: List<NoteSchema>): List<Note> = notes.map(::toNote)

    fun toNote(note: NoteSchema): Note =
        Note(
            noteIdentifier = NoteIdentifier(note.apiId),
            title = note.title,
            content = note.content,
            lastModificationTimestamp = note.lastModificationTimestamp,
            creationTimestamp = note.creationTimestamp
        )

    fun toSchema(note: Note): NoteSchema =
        NoteSchema(
            apiId = note.noteIdentifier.id,
            title = note.title,
            content = note.content,
            lastModificationTimestamp = note.lastModificationTimestamp,
            creationTimestamp = note.creationTimestamp
        )
}
