package server.routes.notes

import io.ktor.application.ApplicationCall
import io.ktor.request.receiveOrNull
import kotlinx.serialization.json.JsonDecodingException
import model.schema.NoteSchema

class NotesCallHelper {

    suspend fun getNoteWithId(call: ApplicationCall): NoteSchema? {
        val noteId = call.parameters["noteId"]?.toIntOrNull() ?: return null
        val note = getNoteSchemaFromBody(call) ?: return null
        return if(noteId >= 0) {
            note.copy(id = noteId)
        } else {
            null
        }
    }

    suspend fun getNoteSchemaFromBody(call: ApplicationCall): NoteSchema? =
        try {
            call.receiveOrNull()
        } catch (e: JsonDecodingException) {
            null
        }
}
