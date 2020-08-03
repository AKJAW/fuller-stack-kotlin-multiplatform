package server.routes.notes

import io.ktor.application.ApplicationCall
import io.ktor.request.receiveOrNull
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonDecodingException
import model.NoteIdentifier

class NotesCallHelper {

    suspend fun getNoteIdentifiersFromBody(call: ApplicationCall): List<NoteIdentifier>? =
        try {
            val ids: List<Int>? = call.receiveOrNull()
            ids?.map { NoteIdentifier(it) }
        } catch (e: JsonDecodingException) {
            null
        }

    suspend inline fun <reified T: Any> getJsonData(call: ApplicationCall): T? =
        try {
            call.receiveOrNull()
        } catch (e: SerializationException) {
            null
        }
}
