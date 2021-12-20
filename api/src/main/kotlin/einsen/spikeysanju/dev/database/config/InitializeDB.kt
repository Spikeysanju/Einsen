package einsen.spikeysanju.dev.database.config

import einsen.spikeysanju.dev.database.table.TaskTable
import einsen.spikeysanju.dev.database.table.UserTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * To initialize connection with our DB
 */
fun initializeDatabase(databaseConfig: DatabaseConfig) {
    val tables = arrayOf(UserTable, TaskTable)

    Database.connect(
        url = databaseConfig.url,
        driver = "org.postgresql.Driver",
        user = databaseConfig.user,
        password = databaseConfig.password
    )

    transaction {
        SchemaUtils.createMissingTablesAndColumns(*tables)
    }
}
