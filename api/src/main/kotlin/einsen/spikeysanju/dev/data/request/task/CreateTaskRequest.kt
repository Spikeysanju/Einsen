package einsen.spikeysanju.dev.data.request.task

import kotlinx.serialization.Serializable

@Serializable
data class CreateTaskRequest(
    val title: String,
    val description: String,
    val category: String,
    val emoji: String,
    val urgency: Int,
    val importance: Int,
    val priority: Int,
    val due: String,
    val isCompleted: Boolean
)
