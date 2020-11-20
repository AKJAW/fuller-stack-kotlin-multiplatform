package server.jwt

import com.auth0.jwk.UrlJwkProvider
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.impl.JWTParser
import java.security.interfaces.RSAPublicKey
import java.util.*

class TokenParser(val domain: String) {

    fun getUserId(token: String): String? {
        val jwk = UrlJwkProvider(domain).get(JWT.decode(token).keyId)
        val algorithm = Algorithm.RSA256(jwk.publicKey as RSAPublicKey, null)
        val jwtVerifier = JWT.require(algorithm).build()
        val decoded = jwtVerifier.verify(token)
        val payloadString = String(Base64.getUrlDecoder().decode(decoded.payload))
        val something = JWTParser().parsePayload(payloadString)
        return something.getClaim("sub")?.asString()
    }
}
