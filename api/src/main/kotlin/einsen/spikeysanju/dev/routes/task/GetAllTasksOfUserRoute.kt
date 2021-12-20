package einsen.spikeysanju.dev.routes.task

import einsen.spikeysanju.dev.data.response.EinsenResponse
import einsen.spikeysanju.dev.plugins.userId
import einsen.spikeysanju.dev.service.task.TaskService
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get

fun Route.getAllTasksOfUser(
    service: TaskService
) {
    authenticate {
        get("api/task/get") {
            val userId = call.parameters["userId"] ?: return@get
            val category = call.parameters["category"]

            if (userId != call.userId) {
                call.respond(
                    HttpStatusCode.OK,
                    EinsenResponse<Unit>(false, additionalMessage = "Please use the Bearer token of the User Id mentioned.")
                )
                return@get
            }

            call.respond(
                HttpStatusCode.OK,
                EinsenResponse(true, data = service.getAllTasksOfUser(userId, category ?: ""))
            )
        }
    }
}
