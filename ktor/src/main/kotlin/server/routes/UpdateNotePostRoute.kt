package server.routes

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.patch
import org.kodein.di.instance
import org.kodein.di.ktor.di
import server.routes.helpers.getNoteSchemaFromBody
import server.storage.NotesService

fun Routing.updateNotePatchRoute() {
    val notesService: NotesService by di().instance()

    patch("/notes/{noteId}") {
        val noteId = call.parameters["noteId"]?.toIntOrNull()
        val note = getNoteSchemaFromBody(call)

        if (note == null  || noteId == null || noteId < 0) {
            call.respond(HttpStatusCode.BadRequest)
            return@patch
        }

        call.respond(HttpStatusCode.OK)
        val noteWithId = note.copy(id = noteId)
        notesService.updateNote(noteWithId)
    }
}
