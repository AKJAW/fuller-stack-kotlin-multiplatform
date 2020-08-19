package server.storage

import feature.AddNotePayload
import feature.DeleteNotePayload
import feature.UpdateNotePayload
import model.toCreationTimestamp
import model.toLastModificationTimestamp
import network.NoteSchema
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import server.logger.ApiLogger
import server.storage.model.NotesTable

class NotesService(
    private val database: ExposedDatabase,
    private val apiLogger: ApiLogger
) {

    suspend fun getNotes(): List<NoteSchema> = queryDatabase {
        NotesTable
            .selectAll()
            .orderBy(NotesTable.creationUnixTimestamp to SortOrder.DESC)
            .map { row ->
                NoteSchema(
                    apiId = row[NotesTable.id].value,
                    title = row[NotesTable.title],
                    content = row[NotesTable.content],
                    lastModificationTimestamp = row[NotesTable.lastModificationUnixTimestamp].toLastModificationTimestamp(),
                    creationTimestamp = row[NotesTable.creationUnixTimestamp].toCreationTimestamp(),
                    wasDeleted = row[NotesTable.wasDeleted]
                )
            }
    }

    suspend fun addNote(payload: AddNotePayload): Int = queryDatabase {
        val addedId = NotesTable.insertAndGetId {
            it[title] = payload.title
            it[content] = payload.content
            it[lastModificationUnixTimestamp] = payload.lastModificationTimestamp.unix
            it[creationUnixTimestamp] = payload.creationTimestamp.unix
            it[wasDeleted] = false
        }

        return@queryDatabase addedId.value
    }

    suspend fun updateNote(payload: UpdateNotePayload): Boolean = queryDatabase {
        val updatedAmount = NotesTable.update({
            NotesTable.creationUnixTimestamp eq payload.creationTimestamp.unix
        }) {
            it[title] = payload.title
            it[content] = payload.content
            it[lastModificationUnixTimestamp] = payload.lastModificationTimestamp.unix
        }
        apiLogger.log("NoteService updateNote", "updatedAmount: $updatedAmount")
        return@queryDatabase updatedAmount > 0
    }

    suspend fun deleteNote(deleteNotePayload: DeleteNotePayload): Boolean = queryDatabase {
        val deletedAmount = NotesTable.update({
            NotesTable.creationUnixTimestamp eq deleteNotePayload.creationTimestamp.unix
        }) {
            it[wasDeleted] = deleteNotePayload.wasDeleted
        }
        apiLogger.log("NoteService deleteNote", "deletedAmount: $deletedAmount")
        return@queryDatabase deletedAmount > 0
    }
}
