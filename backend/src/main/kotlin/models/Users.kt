package org.example.models

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object Users : IntIdTable() {
    val username = varchar("username", 255).uniqueIndex()
    val passwordHash = varchar("password_hash", 255)
}
