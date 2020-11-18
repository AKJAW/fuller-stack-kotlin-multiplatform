package tests

import feature.AddNotePayload
import feature.UpdateNotePayload
import model.CreationTimestamp
import model.Note
import model.toLastModificationTimestamp
import network.NoteApi
import network.NoteSchema

@Suppress("TooGenericExceptionThrown")
class NoteApiTestFake : NoteApi {
    var notes = mutableListOf<NoteSchema>()
    var willFail = false

    fun initializeSchemas(notes: List<Note>) {
        this.notes = notes.map { note ->
            NoteSchema(
                apiId = -1,
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
        val lastNoteId = notes.maxByOrNull { it.apiId }?.apiId ?: -1
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
        val oldNote = notes.first { it.creationTimestamp == updatedNotePayload.creationTimestamp }
        val index = notes.indexOf(oldNote)
        notes.removeAt(index)
        val entity = oldNote.copy(
            title = updatedNotePayload.title,
            content = updatedNotePayload.content,
            lastModificationTimestamp = updatedNotePayload.lastModificationTimestamp
        )
        notes.add(index, entity)
    }

    override suspend fun deleteNotes(
        creationTimestamps: List<CreationTimestamp>,
        lastModificationTimestamp: Long
    ) = runOrFail {
        notes = notes.setWasDeleted(creationTimestamps, true, lastModificationTimestamp)
    }

    override suspend fun restoreNotes(
        creationTimestamps: List<CreationTimestamp>,
        lastModificationTimestamp: Long
    ) {
        notes = notes.setWasDeleted(creationTimestamps, false, lastModificationTimestamp)
    }

    private fun MutableList<NoteSchema>.setWasDeleted(
        creationTimestamps: List<CreationTimestamp>,
        wasDeleted: Boolean,
        lastModificationTimestamp: Long
    ): MutableList<NoteSchema> {
        return this.map { noteSchema ->
            if (creationTimestamps.contains(noteSchema.creationTimestamp)) {
                noteSchema.copy(
                    wasDeleted = wasDeleted,
                    lastModificationTimestamp = lastModificationTimestamp.toLastModificationTimestamp()
                )
            } else {
                noteSchema
            }
        }.toMutableList()
    }

    private fun <T> runOrFail(block: () -> T): T {
        if (willFail) throw Throwable()
        return block()
    }
}
