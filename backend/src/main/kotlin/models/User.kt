package org.example.models

import kotlinx.serialization.Serializable

// Data class for a user. This will be used for JSON serialization/deserialization.
@Serializable
data class User(val username: String)

// Data class for the registration request body.
@Serializable
data class RegisterRequest(val username: String, val password: String)

// Data class for the login request body.
@Serializable
data class LoginRequest(val username: String, val password: String)

// Data class for the login response, including the JWT token.
@Serializable
data class LoginResponse(val message: String, val token: String)