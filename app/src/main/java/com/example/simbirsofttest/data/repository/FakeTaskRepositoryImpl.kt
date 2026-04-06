package com.example.simbirsofttest.data.repository

import com.example.simbirsofttest.data.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeTaskRepository : TaskRepository {
    private val tasks = MutableStateFlow<List<Task>>(emptyList())

    override fun getAllTasks(): Flow<List<Task>> = tasks

    override suspend fun addTask(task: Task) {
        val currentList = tasks.value.toMutableList()
        currentList.add(task)
        tasks.value = currentList
    }

    fun emitTasks(newTasks: List<Task>) {
        tasks.value = newTasks
    }
}