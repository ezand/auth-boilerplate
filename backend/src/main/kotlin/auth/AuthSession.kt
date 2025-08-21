package org.example.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthSession(val redirectUrl: String) {
    companion object {
        const val NAME = "auth_session"
    }
}
