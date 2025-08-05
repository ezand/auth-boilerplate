package org.example.modules

import io.ktor.server.application.*
import io.ktor.server.config.*
import org.example.db.DatabaseFactory

fun Application.database(config: ApplicationConfig) {
    DatabaseFactory.init(config)
}