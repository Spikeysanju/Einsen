package einsen.spikeysanju.dev.data.repository.user

import einsen.spikeysanju.dev.database.domain.User
import java.util.UUID

interface UserRepository {
    suspend fun createUser(userName: String, password: String): User
    suspend fun isUsernameAvailable(userName: String): Boolean
    suspend fun verifyPasswordForUsername(userName: String, password: String): Boolean
    suspend fun findUserByUsername(userName: String): User?
    suspend fun verifyUsernameBelongsToUserId(userName: String, userId: UUID): Boolean
    fun findByUUID(uuid: UUID): User?
}
