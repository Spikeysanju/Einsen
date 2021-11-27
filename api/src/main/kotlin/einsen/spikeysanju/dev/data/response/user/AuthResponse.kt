package einsen.spikeysanju.dev.data.response.user

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val userId: String,
    val token: String
)
