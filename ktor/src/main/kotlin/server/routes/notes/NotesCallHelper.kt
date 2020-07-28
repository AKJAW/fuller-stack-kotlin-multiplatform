package server.routes.notes

import io.ktor.application.ApplicationCall
import io.ktor.request.receiveOrNull
import kotlinx.serialization.json.JsonDecodingException
import model.NoteIdentifier
import model.schema.NoteSchema

class NotesCallHelper {

    suspend fun getNoteWithId(call: ApplicationCall): NoteSchema? {
        val noteId = call.parameters["noteId"]?.toIntOrNull() ?: return null
        val note = getNoteSchemaFromBody(call) ?: return null
        return if (noteId >= 0) {
            note.copy(apiId = noteId)
        } else {
            null
        }
    }

    suspend fun getNoteIdentifiersFromBody(call: ApplicationCall): List<NoteIdentifier>? =
        try {
            val ids: List<Int>? = call.receiveOrNull()
            ids?.map { NoteIdentifier(it) }
        } catch (e: JsonDecodingException) {
            null
        }

    suspend fun getNoteSchemaFromBody(call: ApplicationCall): NoteSchema? =
        try {
            call.receiveOrNull()
        } catch (e: JsonDecodingException) {
            null
        }
}
