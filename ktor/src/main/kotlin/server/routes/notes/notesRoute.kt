package server.routes.notes

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.patch
import io.ktor.routing.post
import org.kodein.di.instance
import org.kodein.di.ktor.di
import server.storage.NotesService

fun Routing.notesRoute() {
    val notesService: NotesService by di().instance()
    val notesCallHelper: NotesCallHelper by di().instance()

    get("/notes") {
        call.respond(notesService.getNotes())
    }

    post("/notes") {
        val note = notesCallHelper.getNoteSchemaFromBody(call)
        println(note)

        if (note == null) {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        notesService.addNote(note)
        call.respond(HttpStatusCode.OK)
    }

    patch("/notes/{noteId}") {
        val note = notesCallHelper.getNoteWithId(call)

        if (note == null) {
            call.respond(HttpStatusCode.BadRequest)
            return@patch
        }
        notesService.updateNote(note)
        call.respond(HttpStatusCode.OK)
    }

    delete("/notes/{noteId}") {
        val noteId = call.parameters["noteId"]?.toIntOrNull()

        if (noteId == null) {
            call.respond(HttpStatusCode.BadRequest)
            return@delete
        }

        val wasDeleted = notesService.deleteNote(noteId)
        if(wasDeleted){
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}
