package einsen.spikeysanju.dev.data.repository.task

import einsen.spikeysanju.dev.data.request.task.CreateTaskRequest
import einsen.spikeysanju.dev.database.domain.Task

interface TaskRepository {
    suspend fun createTask(userId: String, task: CreateTaskRequest): String
    suspend fun getAllTask(userId: String, category: String): List<Task>
    suspend fun updateTask(taskId: String, task: CreateTaskRequest): String
    suspend fun deleteTask(taskId: String): Boolean
    suspend fun verifyTaskExistence(taskId: String): Boolean
    suspend fun verifyTaskIsOfUser(userId: String, taskId: String): Boolean
}
