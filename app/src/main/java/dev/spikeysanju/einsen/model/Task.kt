package dev.spikeysanju.einsen.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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
    @ColumnInfo(name = "timer")
    val due: String = "",
    @ColumnInfo(name = "status")
    val status: TaskStatus,
    @ColumnInfo(name = "createdAt")
    val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updatedAt")
    val updatedAt: Long = System.currentTimeMillis(),
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0
)


enum class TaskStatus(status: String) {
    TODO("Todo"),
    DOING("Doing"),
    DONE("Done")
}
