package einsen.spikeysanju.dev.database.entity

import einsen.spikeysanju.dev.database.table.TaskTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class TaskEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<TaskEntity>(TaskTable)

    var user by UserEntity referencedOn TaskTable.user
    var title by TaskTable.title
    var description by TaskTable.description
    var category by TaskTable.category
    var emoji by TaskTable.emoji
    var urgency by TaskTable.urgency
    var importance by TaskTable.importance
    var priority by TaskTable.priority
    var due by TaskTable.due
    var isCompleted by TaskTable.isCompleted
    var created by TaskTable.created
    var updated by TaskTable.updated
}
