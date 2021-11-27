package einsen.spikeysanju.dev

import einsen.spikeysanju.dev.data.response.EinsenResponse
import einsen.spikeysanju.dev.database.config.DatabaseConfig
import einsen.spikeysanju.dev.database.config.initializeDatabase
import einsen.spikeysanju.dev.di.mainModule
import einsen.spikeysanju.dev.plugins.configureMonitoring
import einsen.spikeysanju.dev.plugins.configureRouting
import einsen.spikeysanju.dev.plugins.configureSecurity
import einsen.spikeysanju.dev.plugins.configureSerialization
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import org.koin.core.context.startKoin

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    startKoin {
        modules(mainModule)
    }
    initializeDatabase(DatabaseConfig())
    configureSecurity()
    configureMonitoring()
    configureRouting()
    configureSerialization()
    configureStatus()
}

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
