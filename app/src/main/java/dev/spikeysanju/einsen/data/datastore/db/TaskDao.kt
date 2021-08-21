package dev.spikeysanju.einsen.data.datastore.db

import androidx.room.*
import dev.spikeysanju.einsen.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
    fun getAllTask(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Query("DELETE FROM task where id =:id")
    suspend fun deleteByID(id: Int)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTask(task: Task)

    @Query("SELECT * FROM task where id=:id")
    fun findByID(id: Int): Flow<Task>

    @Query("UPDATE task set isCompleted= :isCompleted where id=:id")
    suspend fun updateStatus(id: Int, isCompleted: Boolean)

    @Query("SELECT * FROM task WHERE priority=:priority")
    fun getTaskByPriority(priority: String): Flow<List<Task>>
}