package einsen.spikeysanju.dev.routes.task

import einsen.spikeysanju.dev.data.request.task.CreateTaskRequest
import einsen.spikeysanju.dev.data.response.EinsenResponse
import einsen.spikeysanju.dev.data.response.task.TaskResponse
import einsen.spikeysanju.dev.plugins.userId
import einsen.spikeysanju.dev.service.task.TaskService
import einsen.spikeysanju.dev.utils.ServiceResult
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveOrNull
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post

fun Route.createTask(
    service: TaskService
) {
    authenticate {
        post("api/task/create") {
            val response = call.receiveOrNull<CreateTaskRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, EinsenResponse<Unit>(false, "Where's my body?"))
                return@post
            }

            val userId = call.userId

            when (val result = service.validateAndCreateTask(userId, response)) {
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
