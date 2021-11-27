package einsen.spikeysanju.dev.routes.user

import einsen.spikeysanju.dev.data.request.user.CreateUserAccountRequest
import einsen.spikeysanju.dev.data.response.EinsenResponse
import einsen.spikeysanju.dev.data.response.user.AuthResponse
import einsen.spikeysanju.dev.service.user.UserService
import einsen.spikeysanju.dev.utils.ServiceResult
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveOrNull
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post

fun Route.login(
    service: UserService
) {
    post("api/user/login") {
        val response = call.receiveOrNull<CreateUserAccountRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, EinsenResponse<Unit>(false, "Where's my body?"))
            return@post
        }

        val userId = service.run { response.userName.getUser()?.id }

        when (val result = service.validateCredentialsForLogin(response.userName, response.password)) {
            is ServiceResult.Success -> call.respond(
                HttpStatusCode.OK,
                EinsenResponse(true, data = AuthResponse(userId ?: return@post, result.message))
            )
            is ServiceResult.Error -> call.respond(
                HttpStatusCode.BadRequest,
                EinsenResponse<Unit>(false, result.message)
            )
        }
    }
}
