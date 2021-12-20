package einsen.spikeysanju.dev.data.response

import kotlinx.serialization.Serializable

/**
 * Default Response class.
 * @param[success] indicates whether the request was actually successful or not, i.e. [data] is not null
 * @param[data] will contain response class only when [success] is true
 * @param[additionalMessage] for any additional message server wants to let you know
 */
@Serializable
data class EinsenResponse<T>(
    val success: Boolean,
    var additionalMessage: String? = null,
    val data: T? = null
)
