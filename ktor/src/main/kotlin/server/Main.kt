package server

import dependencyinjection.common
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.kodein.di.ktor.di

fun Application.module() {
    di {
        import(common)
    }
    install(Routing) {
        get("/") {
            call.respondText("Ktor server", ContentType.Text.Html)
        }
    }
}

fun main() {
    embeddedServer(Netty, port = 9000, module = Application::module).start(wait = true)
}
