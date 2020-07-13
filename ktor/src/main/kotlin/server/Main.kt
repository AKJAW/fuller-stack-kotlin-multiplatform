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
import server.composition.databaseModule
import server.routes.notes.NotesCallHelper
import server.routes.notes.notesRoute
import server.routes.rootRoute

fun main() {
    embeddedServer(
        factory = Netty,
        port = getPort(),
        module = Application::module,
        watchPaths = listOf("ktor")
    ).start(wait = true)
}

@Suppress("MagicNumber")
private fun getPort() = System.getenv("PORT")?.toIntOrNull() ?: 9000

fun Application.module() {
    di {
        import(databaseModule)
        bind() from singleton { NotesCallHelper() }
    }

    install(Routing) {
        rootRoute()
        notesRoute()
    }
    install(ContentNegotiation) {
        json()
    }
}
