package com.example.simbirsofttest.data.repository

import com.example.simbirsofttest.data.Task
import com.example.simbirsofttest.data.cache.CacheModule
import com.example.simbirsofttest.data.cache.toCache
import com.example.simbirsofttest.data.cache.toTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    cacheModule: CacheModule
) : TaskRepository {

    private val dao = cacheModule.dao()

    override fun getAllTasks(): Flow<List<Task>> =
        dao.getAllTasks().map { list -> list.map { it.toTask() } }

    override suspend fun addTask(task: Task) {
        dao.saveTask(task.toCache())
    }
}