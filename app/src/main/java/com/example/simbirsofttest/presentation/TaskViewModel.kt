package com.example.simbirsofttest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simbirsofttest.data.Task
import com.example.simbirsofttest.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate

class TaskViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    private val _allTasks = MutableStateFlow<List<Task>>(emptyList())
    val allTasks: StateFlow<List<Task>> = _allTasks.asStateFlow()

    private val _tasksForDay = MutableStateFlow<List<Task>>(emptyList())
    val tasksForDay: StateFlow<List<Task>> = _tasksForDay.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllTasks().collectLatest { tasks ->
                _allTasks.value = tasks
                filterTasksForSelectedDate(tasks)
            }
        }
    }

    fun selectDate(date: LocalDate) {
        _selectedDate.value = date
        filterTasksForSelectedDate(_allTasks.value)
    }

    private fun filterTasksForSelectedDate(allTasks: List<Task>) {
        val date = _selectedDate.value
        _tasksForDay.value = allTasks.filter { it.getLocalDate() == date }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.addTask(task)
        }
    }
}