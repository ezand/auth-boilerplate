package club.huddleup.admin

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.serialization.Serializable
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.slf4j.LoggerFactory
import java.io.FileInputStream

@Serializable
data class FirebaseUserCreatedEvent(
    val uid: String,
    val email: String?,
    val emailVerified: Boolean,
    val displayName: String?,
    val photoURL: String?,
    val phoneNumber: String?,
    val providerIds: List<String>,
    val createdAt: String
)

fun initFirebase(): FirebaseApp {
    val firebaseFilePath = System.getenv("FIREBASE_ADMIN_FILE")
        ?: throw IllegalStateException("FIREBASE_ADMIN_FILE environment variable not set")

    FileInputStream(firebaseFilePath).use { serviceAccount ->
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()

        // Return the existing app if already initialized, otherwise initialize a new one
        return FirebaseApp.getApps().firstOrNull() ?: FirebaseApp.initializeApp(options)
    }
}

fun syncUsers() {
    val firebaseApp = initFirebase()
    val firebaseAuth = FirebaseAuth.getInstance(firebaseApp)
    val maxUsersPerPage = 1000

    generateSequence(firebaseAuth.listUsers(null, maxUsersPerPage))
    { page -> page.nextPage }
        .flatMap { it.values }
        .forEach { user ->
            // TODO
            println("User: ${user.uid}")
        }
}

class UserSyncJob : Job {
    private val logger = LoggerFactory.getLogger(UserSyncJob::class.java)

    override fun execute(context: JobExecutionContext) {
        logger.info("Starting scheduled user sync from Firebase")
        val startTime = System.currentTimeMillis()

        try {
            syncUsers()
            val duration = System.currentTimeMillis() - startTime
            logger.info("User sync completed successfully in ${duration}ms")
        } catch (e: Exception) {
            val duration = System.currentTimeMillis() - startTime
            logger.error("User sync failed after ${duration}ms", e)
        }
    }
}