package org.example.modules

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.config.*
import io.ktor.server.sessions.*
import org.example.auth.JwtService
import org.example.auth.UserSession

fun Application.authentication(config: ApplicationConfig, jwtService: JwtService) {
    install(Authentication) {
        //authGoogle(config)
        authJwt(jwtService)
    }

    install(Sessions) {
        cookie<UserSession>("user_session")
    }
}
