package server.routes

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.post
import org.kodein.di.instance
import org.kodein.di.ktor.di
import server.routes.helpers.getNoteSchemaFromBody
import server.storage.NotesStorage

fun Routing.addNotePostRoute(){
    val notesStorage: NotesStorage by di().instance<NotesStorage>()

    post("/add-note") {
        val note = getNoteSchemaFromBody(call)
        println(note)

        if(note == null){
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        call.respond(HttpStatusCode.OK)
        notesStorage.addNote(note)
    }
}
