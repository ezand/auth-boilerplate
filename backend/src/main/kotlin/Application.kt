package org.example

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import org.example.auth.JwtService
import org.example.modules.authentication
import org.example.modules.database
import org.example.routes.authRoutes
import org.example.routes.profileRoutes
import org.example.service.UserService

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    val config = environment.config
    val userService = UserService()
    val jwtService = JwtService(config)

    database(config)
    authentication(config, jwtService)

    // Install the ContentNegotiation feature for JSON serialization.
    install(ContentNegotiation) {
        json()
    }

    // Configure the routing.
    routing {
        route("/api") {
            route("/auth") {
                authRoutes(userService, jwtService)
            }
            profileRoutes()
        }
    }
}
