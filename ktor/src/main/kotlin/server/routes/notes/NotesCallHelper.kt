package server.routes.notes

import io.ktor.application.ApplicationCall
import io.ktor.request.receiveOrNull
import kotlinx.serialization.SerializationException

class NotesCallHelper {

    suspend inline fun <reified T : Any> getJsonData(call: ApplicationCall): T? =
        try {
            call.receiveOrNull()
        } catch (e: SerializationException) {
            null
        }
}
