package org.example.modules

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.auth.*
import io.ktor.server.config.*

fun AuthenticationConfig.authGoogle(config: ApplicationConfig) {
    val applicationHttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    oauth("auth-oauth-google") {}
}
