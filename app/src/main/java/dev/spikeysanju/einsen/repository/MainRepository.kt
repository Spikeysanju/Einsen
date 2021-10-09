package dev.spikeysanju.einsen.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import dev.spikeysanju.einsen.data.local.db.EmojisDao
import dev.spikeysanju.einsen.data.local.db.TaskDao
import dev.spikeysanju.einsen.model.emoji.EmojiItem
import dev.spikeysanju.einsen.model.task.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Single source of data for all the Task of the app.
 */

class MainRepository @Inject constructor(
    private val taskDao: TaskDao,
    private val emojisDao: EmojisDao
) {

    /**
     * Get a all Task.
     */
    fun getAllTask(): Flow<List<Task>> =
        taskDao.getAllTask().flowOn(Dispatchers.IO).conflate()

    /**
     * Create a new Task.
     * @param task
     */
    suspend fun insert(task: Task) = taskDao.insertTask(task)

    /**
     * Update a existing Task.
     * @param task
     */
    suspend fun update(task: Task) = taskDao.updateTask(task)

    /**
     * Delete a [Task].
     * @param id
     */
    suspend fun delete(id: Int) = taskDao.deleteTaskByID(id)

    /**
     * Find a Task by it's [ID].
     * @param id
     */

    fun find(id: Int) = taskDao.findByID(id).flowOn(Dispatchers.IO).conflate()

    /**
     * Update a status for a Task by it's [ID].
     * @param id
     * @param isCompleted
     */
    suspend fun updateStatus(id: Int, isCompleted: Boolean) =
        taskDao.updateTaskStatus(id = id, isCompleted = isCompleted)

    /**
     * Get a Task by it's [Priority].
     * @param priority
     */
    fun getTaskByPriority(priority: String): Flow<List<Task>> =
        taskDao.getTaskByPriority(priority).flowOn(Dispatchers.IO).conflate()

    /**
     * Get a Task count by it's [Priority].
     * @param priority
     */
    fun getTaskByPriorityCount(priority: String): Flow<Int> =
        taskDao.getTaskByPriorityCount(priority).flowOn(Dispatchers.IO).conflate()

    /**
     * *********************************************************************************************
     * Emoji's CRUD operations
     */

    /**
     * Get a all [Emojis].
     */
    fun getAllEmojis(): Flow<List<EmojiItem>> =
        emojisDao.getAllEmojis().flowOn(Dispatchers.IO).conflate()

    /**
     * Insert a new [Emojis].
     * @param emojiItem
     */
    suspend fun insert(emojiItem: List<EmojiItem>) = emojisDao.insertEmojis(emojiItem)

    /**
     * Delete all [Emojis].
     */
    suspend fun deleteAllEmojis() = emojisDao.deleteAllEmojis()

    fun getEmojis(): Flow<PagingData<EmojiItem>> {
        return Pager(
            PagingConfig(
                pageSize = 60,
                prefetchDistance = 30,
                enablePlaceholders = false,
                maxSize = 200
            )
        ) { emojisDao.getEmojis() }.flow
    }

    val getAllEmojiss: PagingSource<Int, EmojiItem> = emojisDao.getEmojis()

}
