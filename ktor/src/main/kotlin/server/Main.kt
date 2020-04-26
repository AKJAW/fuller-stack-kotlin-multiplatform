package server

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import sample.hello

fun Application.module() {
    install(Routing) {
        get("/") {
            val text = hello()
            call.respondText("$text Ktor", ContentType.Text.Html)
        }
    }
}

fun main() {
    embeddedServer(Netty, 9000, module = Application::module).start(wait = true)
}