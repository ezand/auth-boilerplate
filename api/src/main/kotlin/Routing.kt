package club.huddleup

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("/api") {
            get("") {
                call.respondText("Hello World!")
            }
            get("/json") {
                call.respond(mapOf("hello" to "world"))
            }

            authenticate("firebase") {
                get("/me") {
                    val principal = call.principal<UserIdPrincipal>()
                    log.info("Principal: $principal")
                    call.respond(mapOf("hello" to "world"))
                }
            }
        }

        route("/internal/api") {
            authenticate("bearerFirebase") {
                post("/firebase/users") {
                    val body = call.receiveText()
                    log.info("User created: $body")
                    call.respond(HttpStatusCode.Created)
                }
            }
        }
    }
}
