package einsen.spikeysanju.dev.routes.auth

import einsen.spikeysanju.dev.data.request.auth.CreateAccountRequest
import einsen.spikeysanju.dev.data.response.EinsenResponse
import einsen.spikeysanju.dev.data.response.auth.AuthResponse
import einsen.spikeysanju.dev.service.auth.AuthService
import einsen.spikeysanju.dev.utils.ServiceResult
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveOrNull
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post

fun Route.registerAuthenticationRoutes(
    service: AuthService
) {
    post("api/auth/login") {
        val response = call.receiveOrNull<CreateAccountRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, EinsenResponse<Unit>(false, "Where's my body?"))
            return@post
        }

        val userId = service.run { response.username.getUser()?.id }

        when (val result = service.validateCredentialsForLogin(response.username, response.password)) {
            is ServiceResult.Success -> call.respond(
                HttpStatusCode.OK,
                EinsenResponse(true, data = AuthResponse(userId ?: return@post, result.message))
            )
            is ServiceResult.Error -> call.respond(
                HttpStatusCode.OK,
                EinsenResponse<Unit>(false, result.message)
            )
        }
    }

    post("api/auth/register") {
        val response = call.receiveOrNull<CreateAccountRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, EinsenResponse<Unit>(false, "Where's my body?"))
            return@post
        }
        when (val result = service.validateCredentialsForRegistration(response.username, response.password)) {
            is ServiceResult.Success -> call.respond(HttpStatusCode.OK, EinsenResponse<Unit>(true, result.message))
            is ServiceResult.Error -> call.respond(
                HttpStatusCode.OK,
                EinsenResponse<Unit>(false, result.message)
            )
        }
    }
}
