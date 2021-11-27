package einsen.spikeysanju.dev.database.table

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.jodatime.datetime
import org.joda.time.DateTime

object TaskTable : UUIDTable() {
    val user = reference("user", UserTable)
    var title = varchar("note_title", length = 50)
    var description = text("description")
    var category = varchar("category", length = 50)
    var emoji = varchar("emoji", length = 10)
    var urgency = integer("urgency")
    var importance = integer("importance")
    var priority = integer("priority")
    var due = varchar("due", length = 50)
    var isCompleted = bool("isCompleted")
    var created = datetime("created").default(DateTime.now())
    var updated = datetime("updated").default(DateTime.now())
}
