package club.huddleup

import com.kborowy.authprovider.firebase.firebase
import io.ktor.server.application.*
import io.ktor.server.auth.*
import java.io.File

fun Application.configureSecurity() {
    val firebaseAdminFile = File(System.getenv("FIREBASE_ADMIN_FILE"))

    install(Authentication) {
        firebase {
            realm = "Auth API"

            setup { adminFile = firebaseAdminFile }

            validate { token ->
                UserIdPrincipal(token.uid)
            }
        }
    }
}
