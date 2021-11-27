package einsen.spikeysanju.dev.data.request.user

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserAccountRequest(
    val userName: String,
    val password: String
)
