package einsen.spikeysanju.dev.data.repository.task

import einsen.spikeysanju.dev.data.request.task.CreateTaskRequest
import einsen.spikeysanju.dev.database.domain.Task
import einsen.spikeysanju.dev.database.domain.Task.Companion.toDomain
import einsen.spikeysanju.dev.database.entity.TaskEntity
import einsen.spikeysanju.dev.database.entity.UserEntity
import einsen.spikeysanju.dev.database.table.TaskTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

interface TaskRepository {
    suspend fun createTask(userId: String, task: CreateTaskRequest): String
    suspend fun getAllTask(userId: String, category: String): List<Task>
    suspend fun updateTask(taskId: String, task: CreateTaskRequest): String
    suspend fun deleteTask(taskId: String): Boolean
    suspend fun verifyTaskExistence(taskId: String): Boolean
    suspend fun verifyTaskIsOfUser(userId: String, taskId: String): Boolean
}

class TaskRepositoryImpl : TaskRepository {
    override suspend fun getAllTask(userId: String, category: String): List<Task> = transaction {
        val tasks = if (category.trim().isBlank()) {
            TaskTable.user eq UUID.fromString(userId)
        } else {
            (TaskTable.user eq UUID.fromString(userId)) and (TaskTable.category eq category)
        }

        TaskEntity.find { tasks }
            .sortedByDescending { it.id }
            .map { it.toDomain() }
    }

    override suspend fun createTask(userId: String, task: CreateTaskRequest): String = transaction {
        TaskEntity.new {
            this.user = UserEntity[UUID.fromString(userId)]
            this.title = task.title
            this.description = task.description
            this.category = task.category
            this.emoji = task.emoji
            this.urgency = task.urgency
            this.importance = task.importance
            this.priority = task.priority
            this.due = task.due
            this.isCompleted = task.isCompleted
        }.id.value.toString()
    }

    override suspend fun updateTask(taskId: String, task: CreateTaskRequest): String = transaction {
        TaskEntity[UUID.fromString(taskId)].apply {
            this.title = task.title
            this.description = task.description
            this.category = task.category
            this.emoji = task.emoji
            this.urgency = task.urgency
            this.importance = task.importance
            this.priority = task.priority
            this.due = task.due
            this.isCompleted = task.isCompleted
        }.id.value.toString()
    }

    override suspend fun deleteTask(taskId: String): Boolean = transaction {
        TaskEntity.findById(UUID.fromString(taskId))?.run {
            delete()
            return@transaction true
        }
        return@transaction false
    }

    override suspend fun verifyTaskExistence(taskId: String): Boolean = transaction {
        TaskEntity.findById(UUID.fromString(id)) == null
    }

    override suspend fun verifyTaskIsOfUser(userId: String, taskId: String): Boolean = transaction {
        TaskEntity.find {
            (TaskTable.user eq UUID.fromString(userId)) and (TaskTable.id eq UUID.fromString(taskId))
        }.firstOrNull() != null
    }
}
