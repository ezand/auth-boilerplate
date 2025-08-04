package org.example.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.config.*
import org.example.models.User
import java.util.*

class JwtService(config: ApplicationConfig) {
    private val jwtConfig = config.config("jwt")
    private val jwtSecret = jwtConfig.property("secret").getString()
    private val jwtIssuer = jwtConfig.property("issuer").getString()
    private val jwtAudience = jwtConfig.property("audience").getString()
    private val jwtRealm = "ktor-jwt-auth"

    // The algorithm used for signing the token
    private val algorithm = Algorithm.HMAC256(jwtSecret)

    // Generates a JWT for a given user
    fun generateToken(user: User): String {
        return JWT.create()
            .withAudience(jwtAudience)
            .withIssuer(jwtIssuer)
            .withClaim("username", user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + 60000)) // Token expires in 1 minute
            .sign(algorithm)
    }

    fun getJwtRealm(): String = jwtRealm
    fun getJwtAudience(): String = jwtAudience
    fun getJwtIssuer(): String = jwtIssuer
    fun getAlgorithm(): Algorithm = algorithm
}
