package server

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.routing.Routing
import io.ktor.serialization.json
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import server.routes.notesGetRoute
import server.routes.rootGetRoute

fun Application.module() {
    install(Routing) {
        rootGetRoute()
        notesGetRoute()
    }
    install(ContentNegotiation) {
        json()
    }
}

fun main() {
    embeddedServer(
        factory = Netty,
        port = 9000,
        module = Application::module,
        watchPaths = listOf("ktor")
    ).start(wait = true)
}
