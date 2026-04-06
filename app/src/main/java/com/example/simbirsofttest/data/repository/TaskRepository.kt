package com.example.simbirsofttest.data.repository

import com.example.simbirsofttest.data.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<Task>>
    suspend fun addTask(task: Task)
}