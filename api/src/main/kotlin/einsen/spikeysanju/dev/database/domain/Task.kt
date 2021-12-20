package einsen.spikeysanju.dev.database.domain

import einsen.spikeysanju.dev.database.entity.TaskEntity
import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: String = "",
    val title: String,
    val description: String,
    val category: String,
    val emoji: String,
    val urgency: Int,
    val importance: Int,
    val priority: Int,
    val due: String,
    val isCompleted: Boolean,
    val created: Long,
    val updated: Long
) {
    companion object {
        fun TaskEntity.toDomain(): Task {
            return Task(
                id = id.toString(),
                title = title,
                description = description,
                category = category,
                emoji = emoji,
                urgency = urgency,
                importance = importance,
                priority = priority,
                due = due,
                isCompleted = isCompleted,
                created = created.millis,
                updated = updated.millis
            )
        }
    }
}
