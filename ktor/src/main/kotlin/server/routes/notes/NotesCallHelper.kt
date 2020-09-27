package server.routes.notes

import io.ktor.application.ApplicationCall
import io.ktor.request.receiveOrNull
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonDecodingException
import model.CreationTimestamp

class NotesCallHelper {

    suspend fun getCreationTimestampsFromBody(call: ApplicationCall): List<CreationTimestamp>? =
        try {
            call.receiveOrNull()
        } catch (e: JsonDecodingException) {
            null
        }

    suspend inline fun <reified T : Any> getJsonData(call: ApplicationCall): T? =
        try {
            call.receiveOrNull()
        } catch (e: SerializationException) {
            null
        }
}
