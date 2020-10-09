package server

import com.auth0.jwk.UrlJwkProvider
import com.typesafe.config.ConfigFactory
import io.ktor.application.Application
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
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.serialization.json
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.kodein.di.ktor.di
import server.composition.baseModule
import server.composition.databaseModule
import server.routes.notes.notesRoute

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
        import(baseModule)
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

    install(Routing) {
        get("/") {
            call.respondText("Ktor server", ContentType.Text.Html)
        }
        authenticate {
            notesRoute()
        }
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
        host("*") // TODO change when react goes to production
    }
}
