package einsen.spikeysanju.dev.service.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import einsen.spikeysanju.dev.data.repository.auth.AuthRepository
import einsen.spikeysanju.dev.utils.ServiceResult
import einsen.spikeysanju.dev.utils.containsOnlyNumbers
import einsen.spikeysanju.dev.utils.containsSpecialCharacters
import java.util.*

/**
 * Class responsible to verify our login request and returns appropriate result.
 */
class AuthService(
    private val authRepository: AuthRepository,
    private val jwtDomain: String,
    private val jwtAudience: String,
    private val jwtSecret: String
) {

    private val encryptorService = EncryptorService(jwtSecret)

    suspend fun String.getUser() = authRepository.findUserByUsername(this)

    suspend fun validateCredentialsForRegistration(userName: String, password: String): ServiceResult {
        return when {
            (userName.isBlank() || password.isBlank()) -> ServiceResult.Error("Required fields cannot be blank")
            (userName.trim().length !in (4..10)) -> ServiceResult.Error("Username length should be between 4 to 10 characters")
            (password.trim().length !in (4..20)) -> ServiceResult.Error("Password length should be between 4 to 20 characters")
            userName.containsOnlyNumbers() -> ServiceResult.Error("Username should not only consists of numbers")
            userName.containsSpecialCharacters() -> ServiceResult.Error("Special characters are not allowed inside username")
            !authRepository.isUsernameAvailable(userName) -> ServiceResult.Error("The username $userName is not available")
            else -> registerNewUser(userName, encryptorService.encryptPassword(password))
        }
    }

    suspend fun validateCredentialsForLogin(userName: String, password: String): ServiceResult {
        return when {
            (userName.isBlank() || password.isBlank()) -> ServiceResult.Error("Required fields cannot be blank")
            authRepository.isUsernameAvailable(userName) -> ServiceResult.Error("Invalid Credentials")
            !authRepository.verifyPasswordForUsername(
                userName,
                encryptorService.encryptPassword(password)
            ) -> ServiceResult.Error("Invalid Credentials")
            else -> loginUserAndGenerateToken(userName)
        }
    }

    private suspend fun registerNewUser(userName: String, password: String): ServiceResult {
        authRepository.createUser(userName, password).run {
            return ServiceResult.Success("Account created successfully.")
        }
    }

    private suspend fun loginUserAndGenerateToken(userName: String): ServiceResult {
        authRepository.findUserByUsername(userName)?.run {
            val expiresIn = 4102504735000L
            val token = JWT.create()
                .withClaim("userId", id)
                .withIssuer(jwtDomain)
                .withExpiresAt(Date(System.currentTimeMillis() + expiresIn))
                .withAudience(jwtAudience)
                .sign(Algorithm.HMAC256(jwtSecret))
            return ServiceResult.Success(token)
        }
        return ServiceResult.Error("Failed to log in, please try again later.")
    }
}
