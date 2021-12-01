package einsen.spikeysanju.dev.data.request.auth

import kotlinx.serialization.Serializable

/**
 * Needed by our API to register a new user into Einsen.
 */
@Serializable
data class CreateAccountRequest(
    val username: String,
    val password: String
)
