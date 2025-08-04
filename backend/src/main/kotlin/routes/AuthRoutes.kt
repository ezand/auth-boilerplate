package org.example.routes

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.auth.JwtService
import org.example.models.*
import org.example.service.UserService
import org.jetbrains.exposed.v1.core.ResultRow
import org.mindrot.jbcrypt.BCrypt

// Extension function to convert an Exposed ResultRow to a User object.
fun ResultRow.toUser() = User(
    username = this[Users.username]
)

// The routing module for authentication.
fun Route.authRoutes(userService: UserService, jwtService: JwtService) {
    // Route for user registration.
    post("/register") {
        val registerRequest = call.receive<RegisterRequest>()

        // Check if a user with the same username already exists.
        val existingUser = userService.findUser(registerRequest.username)
        if (existingUser != null) {
            call.respond(
                HttpStatusCode.Conflict,
                mapOf("error" to "User with this username already exists")
            )
            return@post
        }

        // Hash the password and create the new user.
        val passwordHash = BCrypt.hashpw(registerRequest.password, BCrypt.gensalt())
        val newUserId = userService.createUser(registerRequest.username, passwordHash)

        call.respond(
            HttpStatusCode.Created,
            mapOf("message" to "User registered successfully with ID: $newUserId")
        )
    }

    // Route for user login.
    post("/login") {
        val loginRequest = call.receive<LoginRequest>()

        // Find the user by username.
        val userResultRow = userService.findUser(loginRequest.username)
        if (userResultRow == null) {
            call.respond(
                HttpStatusCode.Unauthorized,
                mapOf("error" to "Invalid username or password")
            )
            return@post
        }

        // Compare the provided password with the stored hash.
        val isPasswordCorrect = BCrypt.checkpw(
            loginRequest.password,
            userResultRow[Users.passwordHash]
        )

        if (isPasswordCorrect) {
            val user = userResultRow.toUser()
            // Generate a JWT token for the authenticated user
            val token = jwtService.generateToken(user)
            call.respond(
                HttpStatusCode.OK,
                LoginResponse(message = "Login successful!", token = token)
            )
        } else {
            call.respond(
                HttpStatusCode.Unauthorized,
                mapOf("error" to "Invalid username or password")
            )
        }
    }
}

// Route that requires authentication.
fun Route.profileRoutes() {
    authenticate("auth-jwt") {
        get("/profile") {
            // Get the principal (user) from the JWT token
            val principal = call.principal<JWTPrincipal>()
            val username = principal?.payload?.getClaim("username")?.asString()
            call.respond(HttpStatusCode.OK, mapOf("message" to "Welcome to your profile, $username!"))
        }
    }
}