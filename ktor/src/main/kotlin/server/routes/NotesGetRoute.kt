package server.routes

import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import org.kodein.di.instance
import org.kodein.di.ktor.di
import server.storage.NotesService

fun Routing.notesGetRoute(){
    val notesStorage by di().instance<NotesService>()

    get("/notes") {
        call.respond(notesStorage.getNotes())
    }
}
