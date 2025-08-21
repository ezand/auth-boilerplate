package club.huddleup

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("/auth") {
            get("") {
                call.respondText("Hello World!")
            }
            get("/json") {
                call.respond(mapOf("hello" to "world"))
            }
        }
    }
}
