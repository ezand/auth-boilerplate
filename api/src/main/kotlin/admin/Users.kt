package club.huddleup.admin

import kotlinx.serialization.Serializable

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
