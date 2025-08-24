package club.huddleup

import com.kborowy.authprovider.firebase.firebase
import io.ktor.server.application.*
import io.ktor.server.auth.*
import java.io.File

fun Application.configureSecurity() {
    val firebaseAdminFile = File(System.getenv("FIREBASE_ADMIN_FILE"))
    val bearerAuthClients = mapOf(
        // Used for authentication event callbacks from Firebase (created, deleted etc.)
        System.getenv("FIREBASE_AUTH_EVENT_TOKEN") to "system/firebase"
    )

    install(Authentication) {
        firebase("firebase") {
            realm = "firebase-auth"

            setup { adminFile = firebaseAdminFile }

            validate { token ->
                UserIdPrincipal(token.uid)
            }
        }

        bearer("bearerFirebase") {
            authenticate { tokenCredential ->
                bearerAuthClients[tokenCredential.token]?.let { clientId ->
                    UserIdPrincipal(clientId)
                }
            }
        }
    }
}
