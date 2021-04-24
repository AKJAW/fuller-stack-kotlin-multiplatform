package server

import com.auth0.jwk.UrlJwkProvider
import com.typesafe.config.ConfigFactory
import io.ktor.application.Application
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.authenticate
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.cio.websocket.pingPeriod
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.serialization.json
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import io.ktor.util.generateNonce
import io.ktor.websocket.WebSockets
import org.kodein.di.ktor.di
import server.composition.baseModule
import server.composition.databaseModule
import server.composition.socketModule
import server.routes.notes.notesRoute
import server.routes.notes.notesSocket
import java.time.Duration

fun main() {
    embeddedServer(
        factory = Netty,
        port = System.getenv("PORT")?.toIntOrNull() ?: 9000,
        module = Application::module,
        watchPaths = listOf("ktor")
    ).start(wait = true)
}

data class NotesSession(val id: String)

fun Application.module() {
    di {
        import(baseModule)
        import(databaseModule)
        import(socketModule)
    }

    install(Authentication) {
        jwt {
            val config = ConfigFactory.load()
            verifier(UrlJwkProvider(config.getString("ktor.domain")))

            validate { jwtCredential ->
                val payload = jwtCredential.payload
                if (payload.audience.contains(config.getString("ktor.audience"))) {
                    JWTPrincipal(payload)
                } else {
                    null
                }
            }
        }
    }

    install(ContentNegotiation) {
        json()
    }

    install(WebSockets) {
        pingPeriod = Duration.ofMinutes(1)
    }

    install(Sessions) {
        cookie<NotesSession>("SESSION")
    }

    intercept(ApplicationCallPipeline.Features) {
        if (call.sessions.get<NotesSession>() == null) {
            call.sessions.set(NotesSession(generateNonce()))
        }
    }

    install(CORS) {
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Patch)
        method(HttpMethod.Delete)
        header(HttpHeaders.AccessControlAllowHeaders)
        header(HttpHeaders.ContentType)
        header(HttpHeaders.AccessControlAllowOrigin)
        header(HttpHeaders.Authorization)
        host("*") // TODO change when react goes to production
    }

    routing {
        get("/") {
            call.respondText("Ktor server", ContentType.Text.Html)
        }
        notesSocket()
        authenticate {
            notesRoute()
        }
    }
}
