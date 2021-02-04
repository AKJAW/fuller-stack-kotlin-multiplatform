package network

import TokenProvider
import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.websocket.WebSockets
import io.ktor.client.request.header

class HttpClientFactory(
    private val tokenProvider: TokenProvider
) {

    fun create(): HttpClient {
        return HttpClient(Js) {
            defaultRequest {
                header("Authorization", "Bearer ${tokenProvider.accessToken}")
            }
            install(WebSockets)
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    }
}
