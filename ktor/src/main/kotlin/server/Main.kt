package server

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.routing.Routing
import io.ktor.serialization.json
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.ktor.di
import org.kodein.di.singleton
import server.routes.addNotePostRoute
import server.routes.notesGetRoute
import server.routes.rootGetRoute
import server.routes.updateNotePostRoute
import server.storage.ExposedDatabase
import server.storage.H2Database
import server.storage.NotesService

fun main() {
    embeddedServer(
        factory = Netty,
        port = getPort(),
        module = Application::module,
        watchPaths = listOf("ktor")
    ).start(wait = true)
}

fun Application.module() {
    di {
        bind<ExposedDatabase>() with singleton { H2Database() }
        bind() from singleton { NotesService(instance()) }
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

private fun getPort() = System.getenv("PORT")?.toIntOrNull() ?: 9000
