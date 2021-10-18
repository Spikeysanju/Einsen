/*
 *
 *  * Copyright 2021 Spikey Sanju
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     https://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

package dev.spikeysanju.einsen.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.spikeysanju.einsen.model.task.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
    fun getAllTask(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Query("DELETE FROM task where id =:id")
    suspend fun deleteTaskByID(id: Int)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTask(task: Task)

    @Query("SELECT * FROM task where id=:id")
    fun findByID(id: Int): Flow<Task>

    @Query("UPDATE task set isCompleted= :isCompleted where id=:id")
    suspend fun updateTaskStatus(id: Int, isCompleted: Boolean)

    @Query("SELECT * FROM task WHERE priority=:priority")
    fun getTaskByPriority(priority: String): Flow<List<Task>>

    @Query("SELECT COUNT(id) FROM task WHERE priority=:priority")
    fun getTaskByPriorityCount(priority: String): Flow<Int>
}
