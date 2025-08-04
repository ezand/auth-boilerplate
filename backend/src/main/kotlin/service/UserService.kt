package org.example.service

import org.example.db.DatabaseFactory.dbQuery
import org.example.models.Users
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.select

class UserService {
    suspend fun createUser(username: String, passwordHash: String): Int = dbQuery {
        Users.insert {
            it[Users.username] = username
            it[Users.passwordHash] = passwordHash
        }[Users.id].value
    }

    suspend fun findUser(username: String) = dbQuery {
        Users.select(Users.username, Users.passwordHash)
            .where { Users.username eq username }
            .singleOrNull()
    }
}
