package einsen.spikeysanju.dev.data.repository.user

import einsen.spikeysanju.dev.database.domain.User
import einsen.spikeysanju.dev.database.entity.UserEntity
import einsen.spikeysanju.dev.database.table.UserTable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class UserRepositoryImpl : UserRepository {
    override suspend fun createUser(userName: String, password: String) = transaction {
        UserEntity.new {
            this.username = userName
            this.password = password
        }.let { User.fromEntity(it) }
    }

    override suspend fun isUsernameAvailable(userName: String): Boolean {
        return transaction {
            UserEntity.find { UserTable.userName eq userName }.firstOrNull()
        } == null
    }

    override suspend fun verifyPasswordForUsername(userName: String, password: String): Boolean {
        return transaction {
            UserEntity.find { (UserTable.userName eq userName) and (UserTable.password eq password) }.firstOrNull()
        } != null
    }

    override suspend fun findUserByUsername(userName: String): User? {
        return transaction {
            UserEntity.find { UserTable.userName eq userName }.firstOrNull()?.let {
                User.fromEntity(it)
            }
        }
    }

    override suspend fun verifyUsernameBelongsToUserId(userName: String, userId: UUID): Boolean {
        return transaction {
            UserEntity.find { (UserTable.userName eq userName) and (UserTable.id eq userId) }.firstOrNull()
        } != null
    }

    override fun findByUUID(uuid: UUID): User? = transaction {
        UserEntity.findById(uuid)
    }?.let { User.fromEntity(it) }
}
