package einsen.spikeysanju.dev.data.response.task

import kotlinx.serialization.Serializable

/**
 * [TaskResponse] will be returned as a response after successful Task creation request wrapped inside
 * [einsen.spikeysanju.dev.data.response.EinsenResponse.data]
 * @param[taskID] Unique ID of the newly created Task
 */
@Serializable
data class TaskResponse(
    val taskID: String
)
