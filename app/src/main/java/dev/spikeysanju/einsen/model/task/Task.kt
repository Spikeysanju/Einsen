package dev.spikeysanju.einsen.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.spikeysanju.einsen.model.task.TaskBuilder

@Entity(tableName = "task")
data class Task(
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "description")
    val description: String = "",
    @ColumnInfo(name = "category")
    val category: String = "",
    @ColumnInfo(name = "Emoji")
    val emoji: String = "",
    @ColumnInfo(name = "urgency")
    val urgency: Float = 0F,
    @ColumnInfo(name = "importance")
    val importance: Float = 0F,
    @ColumnInfo(name = "priority")
    val priority: Priority = Priority.IMPORTANT,
    @ColumnInfo(name = "timer")
    val due: String = "",
    @ColumnInfo(name = "isCompleted")
    val isCompleted: Boolean,
    @ColumnInfo(name = "createdAt")
    val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updatedAt")
    val updatedAt: Long = System.currentTimeMillis(),
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0
)

enum class Priority(count: Int) {
    URGENT(4),
    IMPORTANT(3),
    DELEGATE(2),
    DUMP(1)
}

fun task(block: TaskBuilder.() -> Unit): Task = TaskBuilder().apply(block).build()
