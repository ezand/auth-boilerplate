package org.example.auth

import kotlinx.serialization.Serializable

@Serializable
data class UserSession(val state: String, val token: String)
