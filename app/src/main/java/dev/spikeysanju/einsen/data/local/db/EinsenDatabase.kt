package dev.spikeysanju.einsen.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.spikeysanju.einsen.model.emoji.EmojiItem
import dev.spikeysanju.einsen.model.task.Task

@Database(entities = [Task::class, EmojiItem::class], version = 1, exportSchema = false)
abstract class EinsenDatabase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao
    abstract fun getEmojisDao(): EmojisDao
}
