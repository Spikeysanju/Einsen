package einsen.spikeysanju.dev.service.auth

import einsen.spikeysanju.dev.utils.ALGORITHM
import io.ktor.util.hex
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * Class responsible to encrypt users password.
 */
class EncryptorService(
    secretKey: String
) {

    private val hMacKey = SecretKeySpec(secretKey.toByteArray(), ALGORITHM)

    fun encryptPassword(password: String) = Mac.getInstance(ALGORITHM).run {
        init(hMacKey)
        hex(doFinal(password.toByteArray(Charsets.UTF_8)))
    }
}
