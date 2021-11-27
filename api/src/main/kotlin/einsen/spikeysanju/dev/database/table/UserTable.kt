package einsen.spikeysanju.dev.database.table

import org.jetbrains.exposed.dao.id.UUIDTable

object UserTable : UUIDTable() {
    val userName = varchar("username", length = 30)
    val password = text("password")
}
