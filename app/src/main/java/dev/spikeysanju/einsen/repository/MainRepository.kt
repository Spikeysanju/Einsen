package dev.spikeysanju.einsen.repository

import dev.spikeysanju.einsen.data.datastore.db.TaskDao
import dev.spikeysanju.einsen.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject constructor(private val taskDao: TaskDao) {

    fun getAllTask(): Flow<List<Task>> =
        taskDao.getAllTask().flowOn(Dispatchers.IO).conflate()

    suspend fun insert(task: Task) = taskDao.insertTask(task)

    suspend fun update(task: Task) = taskDao.updateTask(task)

    suspend fun delete(id: Int) = taskDao.deleteTaskByID(id)

    fun find(id: Int) = taskDao.findByID(id).flowOn(Dispatchers.IO).conflate()

    suspend fun updateStatus(id: Int, isCompleted: Boolean) =
        taskDao.updateTaskStatus(id = id, isCompleted = isCompleted)

    fun getTaskByPriority(priority: String): Flow<List<Task>> =
        taskDao.getTaskByPriority(priority).flowOn(Dispatchers.IO).conflate()

    fun getTaskByPriorityCount(priority: String): Flow<Int> =
        taskDao.getTaskByPriorityCount(priority).flowOn(Dispatchers.IO).conflate()
}
