package server.jwt

import com.auth0.jwk.UrlJwkProvider
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.impl.JWTParser
import server.logger.ApiLogger
import java.security.interfaces.RSAPublicKey
import java.util.*

class TokenParser(
    private val domain: String,
    private val apiLogger: ApiLogger
) {

    fun getUserId(token: String): String?  = try {
        val jwk = UrlJwkProvider(domain).get(JWT.decode(token).keyId)
        val algorithm = Algorithm.RSA256(jwk.publicKey as RSAPublicKey, null)
        val jwtVerifier = JWT.require(algorithm).build()
        val decoded = jwtVerifier.verify(token)
        val payloadString = String(Base64.getUrlDecoder().decode(decoded.payload))
        val something = JWTParser().parsePayload(payloadString)
        something.getClaim("sub")?.asString()
    } catch (e: Throwable) {
        apiLogger.log("TokenParser", "error $e")
        null
    }
}
