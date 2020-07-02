package server.routes

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get

fun Routing.notesGetRoute(){
    get("/notes") {
        call.respondText("", ContentType.Text.Html)
    }
}