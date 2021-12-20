package einsen.spikeysanju.dev.routes.task

import einsen.spikeysanju.dev.data.response.EinsenResponse
import einsen.spikeysanju.dev.data.response.task.TaskResponse
import einsen.spikeysanju.dev.plugins.userId
import einsen.spikeysanju.dev.service.task.TaskService
import einsen.spikeysanju.dev.utils.ServiceResult
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.delete

fun Route.deleteTask(
    service: TaskService
) {
    authenticate {
        delete("api/task/delete") {
            val taskId = call.parameters["taskId"] ?: return@delete

            val userId = call.userId

            when (val result = service.verifyAndDeleteTask(userId, taskId)) {
                is ServiceResult.Success -> call.respond(
                    HttpStatusCode.OK,
                    EinsenResponse(true, data = TaskResponse(result.message))
                )
                is ServiceResult.Error -> call.respond(
                    HttpStatusCode.OK,
                    EinsenResponse<Unit>(false, result.message)
                )
            }
        }
    }
}
