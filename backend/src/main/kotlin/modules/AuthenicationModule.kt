package org.example.modules

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.config.*
import io.ktor.server.sessions.*
import org.example.auth.AuthSession
import org.example.auth.JwtService
import org.example.auth.UserSession

fun Application.authentication(config: ApplicationConfig, jwtService: JwtService) {
    install(Sessions) {
        cookie<AuthSession>(AuthSession.NAME)
        cookie<UserSession>(UserSession.NAME)
    }

    install(Authentication) {
        authGoogle(config)
        authJwt(jwtService)
    }
}
