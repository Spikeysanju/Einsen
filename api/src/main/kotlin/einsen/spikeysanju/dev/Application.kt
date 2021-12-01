package einsen.spikeysanju.dev

import einsen.spikeysanju.dev.database.config.DatabaseConfig
import einsen.spikeysanju.dev.database.config.initializeDatabase
import einsen.spikeysanju.dev.di.mainModule
import einsen.spikeysanju.dev.plugins.configureMonitoring
import einsen.spikeysanju.dev.plugins.configureRouting
import einsen.spikeysanju.dev.plugins.configureSecurity
import einsen.spikeysanju.dev.plugins.configureSerialization
import einsen.spikeysanju.dev.plugins.configureStatus
import io.ktor.application.Application
import org.koin.core.context.startKoin

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    startKoin {
        modules(mainModule)
    }
    // TODO("Open DatabaseConfig file and fill important details to run Einsen Server locally")
    initializeDatabase(DatabaseConfig())
    configureSecurity()
    configureMonitoring()
    configureRouting()
    configureSerialization()
    configureStatus()
}
