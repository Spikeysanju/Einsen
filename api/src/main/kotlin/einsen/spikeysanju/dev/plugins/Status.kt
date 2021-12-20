package einsen.spikeysanju.dev.plugins

import einsen.spikeysanju.dev.data.response.EinsenResponse
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond

fun Application.configureStatus() {
    install(StatusPages) {
        status(HttpStatusCode.Unauthorized) {
            call.respond(HttpStatusCode.Unauthorized, EinsenResponse<Unit>(false, "Access Denied"))
        }
        status(HttpStatusCode.InternalServerError) {
            call.respond(HttpStatusCode.InternalServerError, EinsenResponse<Unit>(false, "Internal Server Error. Possible Reasons are (a) Verify Bearer token belongs to the given User ID (b) Given ID may not be a valid UUID String"))
        }
    }
}
