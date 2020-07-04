package server.routes.helpers

import io.ktor.application.ApplicationCall
import io.ktor.request.receiveOrNull
import kotlinx.serialization.json.JsonDecodingException
import model.schema.NoteSchema

suspend fun getNoteSchemaFromBody(call: ApplicationCall): NoteSchema? =
    try {
        call.receiveOrNull()
    } catch (e: JsonDecodingException) {
        null
    }
