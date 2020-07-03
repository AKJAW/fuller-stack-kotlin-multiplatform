package server

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.routing.Routing
import io.ktor.serialization.json
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.kodein.di.bind
import org.kodein.di.ktor.di
import org.kodein.di.singleton
import server.routes.notesGetRoute
import server.routes.rootGetRoute
import server.routes.updateNotePostRoute
import server.storage.NotesStorage

fun Application.module() {
    di {
        bind() from singleton { NotesStorage() }
    }
    install(Routing) {
        rootGetRoute()
        notesGetRoute()
        updateNotePostRoute()
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
