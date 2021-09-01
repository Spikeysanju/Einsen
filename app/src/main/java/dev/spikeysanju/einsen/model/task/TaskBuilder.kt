package dev.spikeysanju.einsen.model.task

class TaskBuilder {
    var title: String = ""
    var description: String = ""
    var category: String = ""
    var emoji: String = ""
    var urgency: Float = 0F
    var importance: Float = 0F
    var priority: Priority = Priority.IMPORTANT
    var due: String = ""
    var isCompleted: Boolean = false
    var createdAt: Long = System.currentTimeMillis()
    var updatedAt: Long = System.currentTimeMillis()
    var id: Int = 0

    fun build(): Task = Task(
        title,
        description,
        category,
        emoji,
        urgency,
        importance,
        priority,
        due,
        isCompleted,
        createdAt,
        updatedAt,
        id
    )
}
