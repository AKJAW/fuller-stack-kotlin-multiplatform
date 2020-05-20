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
import org.kodein.di.erased.instance
import org.kodein.di.ktor.kodein
import sample.Counter

fun Application.module() {
    kodein {
        import(common)
    }
    install(Routing) {
        get("/") {
            val counter by kodein().instance<Counter>()
            call.respondText(counter.getAndIncrement(), ContentType.Text.Html)
        }
    }
}

fun main() {
    embeddedServer(Netty, port = 9000, module = Application::module).start(wait = true)
}
