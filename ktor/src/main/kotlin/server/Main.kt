package server

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.routing.Routing
import io.ktor.serialization.json
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.kodein.di.ktor.di
import server.composition.databaseModule
import server.routes.addNotePostRoute
import server.routes.notesGetRoute
import server.routes.rootGetRoute
import server.routes.updateNotePostRoute

fun main() {
    embeddedServer(
        factory = Netty,
        port = getPort(),
        module = Application::module,
        watchPaths = listOf("ktor")
    ).start(wait = true)
}

private fun getPort() = System.getenv("PORT")?.toIntOrNull() ?: 9000

fun Application.module() {
    di {
        import(databaseModule)
    }

    install(Routing) {
        rootGetRoute()
        notesGetRoute()
        updateNotePostRoute()
        addNotePostRoute()
    }
    install(ContentNegotiation) {
        json()
    }
}
