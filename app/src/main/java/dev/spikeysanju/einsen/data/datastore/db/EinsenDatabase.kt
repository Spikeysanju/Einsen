package dev.spikeysanju.einsen.data.datastore.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.spikeysanju.einsen.model.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class EinsenDatabase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao
}
