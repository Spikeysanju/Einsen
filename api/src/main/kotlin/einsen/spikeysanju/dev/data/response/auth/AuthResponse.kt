package einsen.spikeysanju.dev.data.response.auth

import kotlinx.serialization.Serializable

/**
 * [AuthResponse] will be returned as a response after successful login wrapped inside
 * [einsen.spikeysanju.dev.data.response.EinsenResponse.data]
 * @param[userId] Unique ID of our user
 * @param[token] Unique JWT Token of our user. Can be used to store session at frontend
 */
@Serializable
data class AuthResponse(
    val userId: String,
    val token: String
)
