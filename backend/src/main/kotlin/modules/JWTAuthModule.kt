package org.example.modules

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.example.auth.JwtService

fun AuthenticationConfig.authJwt(jwtService: JwtService) {
    jwt("auth-jwt") {
        realm = jwtService.getJwtRealm()
        verifier(
            com.auth0.jwt.JWT
                .require(jwtService.getAlgorithm())
                .withAudience(jwtService.getJwtAudience())
                .withIssuer(jwtService.getJwtIssuer())
                .build()
        )
        validate { credential ->
            if (credential.payload.getClaim("username").asString() != null) {
                JWTPrincipal(credential.payload)
            } else {
                null
            }
        }
    }
}