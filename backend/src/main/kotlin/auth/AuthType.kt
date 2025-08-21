package org.example.auth

enum class AuthType {
    JWT,
    SESSION;

    companion object {
        fun fromString(value: String): AuthType? {
            return entries.find { it.name.equals(value, ignoreCase = true) }
        }
    }
}