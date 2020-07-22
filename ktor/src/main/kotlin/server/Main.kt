package server

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.routing.Routing
import io.ktor.serialization.json
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.kodein.di.bind
import org.kodein.di.ktor.di
import org.kodein.di.singleton
import server.composition.databaseModule
import server.logger.ApiLogger
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
        bind() from singleton { ApiLogger() }
    }

    install(Routing) {
        rootRoute()
        notesRoute()
    }
    install(ContentNegotiation) {
        json()
    }
    install(CORS) {
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Patch)
        method(HttpMethod.Delete)
        header(HttpHeaders.AccessControlAllowHeaders)
        header(HttpHeaders.ContentType)
        header(HttpHeaders.AccessControlAllowOrigin)
        host("*") //TODO change when react goes to production
    }
}
