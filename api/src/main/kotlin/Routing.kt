package club.huddleup

import io.ktor.server.application.*
import io.ktor.server.auth.*
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
    }
}
