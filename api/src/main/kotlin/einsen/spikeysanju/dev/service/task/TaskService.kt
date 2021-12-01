package einsen.spikeysanju.dev.service.task

import einsen.spikeysanju.dev.data.repository.task.TaskRepository
import einsen.spikeysanju.dev.data.request.task.CreateTaskRequest
import einsen.spikeysanju.dev.utils.ServiceResult

/**
 * Class responsible to verify our Task CRUD request and returns appropriate result.
 */
class TaskService(
    private val taskRepository: TaskRepository
) {

    suspend fun getAllTasksOfUser(userId: String, category: String) = taskRepository.getAllTask(userId, category)

    suspend fun validateAndCreateTask(userId: String, request: CreateTaskRequest): ServiceResult {
        return when {
            (request.title.isBlank() || request.description.isBlank() || request.category.isBlank()) -> {
                ServiceResult.Error("Required fields cannot be blank")
            }
            (request.title.trim().length < 4 || request.description.trim().length < 4 || request.category.trim().length < 4) -> {
                ServiceResult.Error("Required fields length should be at least of 4 characters")
            }
            else -> createTask(userId, request)
        }
    }

    private suspend fun createTask(userId: String, task: CreateTaskRequest): ServiceResult {
        taskRepository.createTask(userId, task).run {
            return ServiceResult.Success(this)
        }
    }

    suspend fun validateAndUpdateTask(userId: String, taskId: String, request: CreateTaskRequest): ServiceResult {
        return when {
            !taskRepository.verifyTaskExistence(taskId) -> ServiceResult.Error("Task with $taskId doesn't exists")
            !taskRepository.verifyTaskIsOfUser(userId, taskId) -> ServiceResult.Error("This is none of your task")
            (request.title.isBlank() || request.description.isBlank() || request.category.isBlank()) -> {
                ServiceResult.Error("Required fields cannot be blank")
            }
            (request.title.trim().length < 4 || request.description.trim().length < 4 || request.category.trim().length < 4) -> {
                ServiceResult.Error("Required fields length should be at least of 4 characters")
            }
            else -> updateTask(taskId, request)
        }
    }

    private suspend fun updateTask(taskId: String, task: CreateTaskRequest): ServiceResult {
        taskRepository.updateTask(taskId, task).run {
            return ServiceResult.Success(this)
        }
    }

    suspend fun verifyAndDeleteTask(userId: String, taskId: String): ServiceResult {
        return when {
            !taskRepository.verifyTaskExistence(taskId) -> ServiceResult.Error("Task with $taskId doesn't exists")
            !taskRepository.verifyTaskIsOfUser(userId, taskId) -> ServiceResult.Error("This is none of your task")
            else -> deleteTask(taskId)
        }
    }

    private suspend fun deleteTask(taskId: String): ServiceResult {
        taskRepository.deleteTask(taskId).run {
            return if (this) ServiceResult.Success("Success") else
                ServiceResult.Error("Error deleting task with $taskId id. Please try again later")
        }
    }
}
