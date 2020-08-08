package tests

import feature.AddNotePayload
import feature.UpdateNotePayload
import model.Note
import network.NoteApi
import network.NoteSchema

@Suppress("TooGenericExceptionThrown")
class NoteApiTestFake : NoteApi {
    var notes = mutableListOf<NoteSchema>()
    var willFail = false

    fun initializeSchemas(notes: List<Note>) {
        this.notes = notes.map { note ->
            NoteSchema(
                apiId = note.noteIdentifier.id,
                title = note.title,
                content = note.content,
                lastModificationTimestamp = note.lastModificationTimestamp,
                creationTimestamp = note.creationTimestamp
            )
        }.toMutableList()
    }

    override suspend fun getNotes(): List<NoteSchema> {
        return notes
    }

    override suspend fun addNote(addNotePayload: AddNotePayload): Int = runOrFail {
        if (willFail) throw Throwable()
        val lastNoteId = notes.maxBy { it.apiId }?.apiId ?: -1
        val newNoteId = lastNoteId + 1

        val newNote = NoteSchema(
            apiId = newNoteId,
            title = addNotePayload.title,
            content = addNotePayload.content,
            lastModificationTimestamp = addNotePayload.lastModificationTimestamp,
            creationTimestamp = addNotePayload.creationTimestamp
        )
        notes.add(newNote)

        return@runOrFail newNoteId
    }

    override suspend fun updateNote(updatedNotePayload: UpdateNotePayload) = runOrFail {
        val oldNote = notes.first { it.apiId == updatedNotePayload.noteId }
        val index = notes.indexOf(oldNote)
        notes.removeAt(index)
        val entity = NoteSchema(
            apiId = updatedNotePayload.noteId,
            title = updatedNotePayload.title,
            content = updatedNotePayload.content,
            lastModificationTimestamp = updatedNotePayload.lastModificationTimestamp,
            creationTimestamp = oldNote.creationTimestamp
        )
        notes.add(index, entity)
    }

    override suspend fun deleteNotes(ids: List<Int>) = runOrFail {
        notes = notes.filterNot { noteSchema ->
            ids.contains(noteSchema.apiId)
        }.toMutableList()
    }

    private fun <T> runOrFail(block: () -> T): T {
        if (willFail) throw Throwable()
        return block()
    }
}
