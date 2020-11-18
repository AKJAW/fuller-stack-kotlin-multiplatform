package server.storage

import feature.AddNotePayload
import feature.DeleteNotePayload
import feature.UpdateNotePayload
import model.toCreationTimestamp
import model.toLastModificationTimestamp
import network.NoteSchema
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import server.logger.ApiLogger
import server.storage.model.NotesTable
import server.storage.model.User

class NotesService(
    private val database: ExposedDatabase,
    private val apiLogger: ApiLogger
) {

    suspend fun getNotes(user: User): List<NoteSchema> = queryDatabase {
        NotesTable
            .select {
                NotesTable.userId eq user.id
            }
            .orderBy(NotesTable.creationUnixTimestamp to SortOrder.DESC)
            .map { row ->
                apiLogger.log("Notes get: ", row.toString())
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

    suspend fun addNote(payload: AddNotePayload, user: User): Int = queryDatabase {
        val addedId = NotesTable.insertAndGetId {
            it[userId] = user.id
            it[title] = payload.title
            it[content] = payload.content
            it[lastModificationUnixTimestamp] = payload.lastModificationTimestamp.unix
            it[creationUnixTimestamp] = payload.creationTimestamp.unix
            it[wasDeleted] = false
        }

        return@queryDatabase addedId.value
    }

    suspend fun updateNote(payload: UpdateNotePayload, user: User): Boolean = queryDatabase {
        val updatedAmount = NotesTable.update({
            (NotesTable.userId eq user.id) and
                    (NotesTable.creationUnixTimestamp eq payload.creationTimestamp.unix)
        }) {
            it[title] = payload.title
            it[content] = payload.content
            it[lastModificationUnixTimestamp] = payload.lastModificationTimestamp.unix
        }
        apiLogger.log("NoteService updateNote", "updatedAmount: $updatedAmount")
        return@queryDatabase updatedAmount > 0
    }

    suspend fun deleteNote(deleteNotePayload: DeleteNotePayload, user: User): Boolean = queryDatabase {
        val deletedAmount = NotesTable.update({
            (NotesTable.userId eq user.id) and
                    (NotesTable.creationUnixTimestamp eq deleteNotePayload.creationTimestamp.unix)
        }) {
            it[wasDeleted] = deleteNotePayload.wasDeleted
            it[lastModificationUnixTimestamp] = deleteNotePayload.lastModificationTimestamp.unix
        }
        apiLogger.log("NoteService deleteNote", "deletedAmount: $deletedAmount")
        return@queryDatabase deletedAmount > 0
    }
}
