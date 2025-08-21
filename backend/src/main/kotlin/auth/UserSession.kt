package org.example.auth

import kotlinx.serialization.Serializable

@Serializable
data class UserSession(val state: String, val token: String, val authType: AuthType) {
    companion object {
        const val NAME = "user_session"
    }
}
