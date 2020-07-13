package server.routes

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get

fun Routing.rootRoute() {
    get("/") {
        call.respondText("Ktor server", ContentType.Text.Html)
    }
}
