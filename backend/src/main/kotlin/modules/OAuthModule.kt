package org.example.modules

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.auth.*
import io.ktor.server.config.*
import io.ktor.server.sessions.*
import org.example.auth.AuthSession

fun AuthenticationConfig.authGoogle(config: ApplicationConfig) {
    val oauthConfig = config.config("oauth").config("google")
    val callbackUrl = oauthConfig.property("callbackUrl").getString()
    val authorizeUrl = oauthConfig.property("authorizeUrl").getString()
    val accessTokenUrl = oauthConfig.property("accessTokenUrl").getString()
    val clientId = oauthConfig.property("clientId").getString()
    val clientSecret = oauthConfig.property("clientSecret").getString()
    val scopes = oauthConfig.property("scopes").getList()

    val applicationHttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    oauth("auth-oauth-google") {
        client = applicationHttpClient
        urlProvider = { callbackUrl }
        providerLookup = {
            OAuthServerSettings.OAuth2ServerSettings(
                name = "google",
                authorizeUrl = authorizeUrl,
                accessTokenUrl = accessTokenUrl,
                requestMethod = HttpMethod.Post,
                clientId = clientId,
                clientSecret = clientSecret,
                defaultScopes = scopes,
                onStateCreated = { call, state ->
                    val redirectUrl = call.request.queryParameters["redirectUrl"] ?: "/"
                    call.sessions.set(AuthSession.NAME, AuthSession(redirectUrl))
                }
            )
        }
    }
}
