package org.example

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import org.example.auth.JwtService
import org.example.db.DatabaseFactory
import org.example.routes.authRoutes
import org.example.routes.profileRoutes
import org.example.service.UserService

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    // Access configuration from the environment
    val config = environment.config

    // Initialize the database and services with the config
    DatabaseFactory.init(config)
    val userService = UserService()
    val jwtService = JwtService(config)

    // Configure the JWT authentication
    install(Authentication) {
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

    // Install the ContentNegotiation feature for JSON serialization.
    install(ContentNegotiation) {
        json()
    }

    // Configure the routing.
    routing {
        authRoutes(userService, jwtService)
        profileRoutes()
    }
}