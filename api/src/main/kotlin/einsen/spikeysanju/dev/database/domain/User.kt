package einsen.spikeysanju.dev.database.domain

import einsen.spikeysanju.dev.database.entity.UserEntity
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val userName: String,
    val password: String
) {
    companion object {
        fun fromEntity(entity: UserEntity): User {
            return User(entity.id.toString(), entity.username, entity.password)
        }
    }
}
