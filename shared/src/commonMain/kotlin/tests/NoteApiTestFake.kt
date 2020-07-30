package tests

import feature.UpdateNotePayload
import model.Note
import model.NoteIdentifier
import model.schema.NoteSchema
import network.NoteApi

@Suppress("TooGenericExceptionThrown")
class NoteApiTestFake : NoteApi {
    var notes = mutableListOf<NoteSchema>()
    var willFail = false

    override suspend fun getNotes(): List<Note> {
        TODO("Not yet implemented")
    }

    override suspend fun addNote(newNote: NoteSchema): Int = runOrFail {
        if (willFail) throw Throwable()
        val lastNoteId = notes.maxBy { it.apiId }?.apiId ?: -1
        val newNoteId = lastNoteId + 1

        val noteWithId = newNote.copy(apiId = newNoteId)
        notes.add(noteWithId)

        return@runOrFail newNoteId
    }

    override suspend fun updateNote(updatedNotePayload: UpdateNotePayload) = runOrFail {
        println(updatedNotePayload)
        println(notes)
        val oldNote = notes.first { it.apiId == updatedNotePayload.noteIdentifier.id }
        val index = notes.indexOf(oldNote)
        notes.removeAt(index)
        val entity = NoteSchema(
            apiId = updatedNotePayload.noteIdentifier.id,
            title = updatedNotePayload.title,
            content = updatedNotePayload.content,
            lastModificationTimestamp = updatedNotePayload.lastModificationTimestamp,
            creationTimestamp = oldNote.creationTimestamp
        )
        notes.add(index, entity)
    }

    override suspend fun deleteNotes(noteIdentifiers: List<NoteIdentifier>) {
        TODO("Not yet implemented")
    }

    private fun <T> runOrFail(block: () -> T): T {
        if (willFail) throw Throwable()
        return block()
    }
}
