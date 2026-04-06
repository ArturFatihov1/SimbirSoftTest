package com.example.simbirsofttest

import com.example.simbirsofttest.data.Task
import com.example.simbirsofttest.data.repository.FakeTaskRepository
import com.example.simbirsofttest.presentation.TaskViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalCoroutinesApi::class)
class TaskViewModelTest {

    // перед запуском теста переключить в appModule флаг runOnTest

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: TaskViewModel
    private lateinit var fakeRepository: FakeTaskRepository

    @Before
    fun setup() {
        fakeRepository = FakeTaskRepository()
        viewModel = TaskViewModel(fakeRepository)
    }

    @Test
    fun `when task added, allTasks state should update correctly`() = runTest {
        val task = Task(1, 1000, 2000, "Test Task", "Description")

        viewModel.addTask(task)

        val currentState = viewModel.allTasks.value
        assertEquals(1, currentState.size)
        assertEquals("Test Task", currentState[0].name)
    }

    @Test
    fun `filtering tasks by date should work with fake data`() = runTest {
        val today = LocalDate.now()
        val start = today.atStartOfDay(ZoneId.systemDefault()).toEpochSecond()

        val taskToday = Task(1, start + 100, start + 500, "Today", "")
        val taskTomorrow = Task(2, start + 100000, start + 105000, "Tomorrow", "")

        fakeRepository.addTask(taskToday)
        fakeRepository.addTask(taskTomorrow)

        viewModel.selectDate(today)

        assertEquals(1, viewModel.tasksForDay.value.size)
        assertEquals("Today", viewModel.tasksForDay.value[0].name)
    }
}

class MainDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
