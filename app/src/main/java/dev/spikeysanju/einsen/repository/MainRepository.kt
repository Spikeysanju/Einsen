package dev.spikeysanju.einsen.repository

import dev.spikeysanju.einsen.data.datastore.db.TaskDao
import dev.spikeysanju.einsen.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject constructor(private val taskDao: TaskDao) {

    suspend fun getAllTask(): Flow<List<Task>> =
        taskDao.getAllTask().flowOn(Dispatchers.IO).conflate()

    suspend fun insert(task: Task) = taskDao.insertSource(task)
    suspend fun update(task: Task) = taskDao.insertSource(task)
    suspend fun delete(id: Int) = taskDao.deleteByID(id)
}