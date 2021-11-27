package einsen.spikeysanju.dev.data.response

import kotlinx.serialization.Serializable

@Serializable
data class EinsenResponse<T>(
    val success: Boolean,
    var message: String? = null,
    val data: T? = null
) {
    init {
        if (success) message = "Success"
    }
}
