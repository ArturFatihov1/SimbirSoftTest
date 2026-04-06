package com.example.simbirsofttest.presentation.screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.simbirsofttest.data.Task
import com.example.simbirsofttest.presentation.TaskViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun TaskNameField(name: String, onNameChange: (String) -> Unit) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text("Название дела *") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun TaskDescriptionField(description: String, onDescriptionChange: (String) -> Unit) {
    OutlinedTextField(
        value = description,
        onValueChange = onDescriptionChange,
        label = { Text("Описание") },
        modifier = Modifier.fillMaxWidth(),
        minLines = 3
    )
}

@Composable
fun DateField(selectedDate: LocalDate, onClick: () -> Unit) {
    OutlinedButton(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Text("Дата: ${selectedDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))}")
    }
}

@Composable
fun TimeField(label: String, time: LocalTime, onClick: () -> Unit) {
    OutlinedButton(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Text("$label: ${time.format(DateTimeFormatter.ofPattern("HH:mm"))}")
    }
}

@Composable
fun CreateTaskButton(
    name: String,
    selectedDate: LocalDate,
    startTime: LocalTime,
    endTime: LocalTime,
    viewModel: TaskViewModel,
    navController: NavController
) {
    Button(
        onClick = {
            if (name.isBlank()) return@Button

            val startDateTime = selectedDate.atTime(startTime)
            val endDateTime = selectedDate.atTime(endTime)

            if (endDateTime.isBefore(startDateTime)) return@Button

            val startTimestamp = startDateTime.atZone(ZoneId.systemDefault()).toEpochSecond()
            val endTimestamp = endDateTime.atZone(ZoneId.systemDefault()).toEpochSecond()

            val newTask = Task(
                id = (viewModel.allTasks.value.maxOfOrNull { it.id } ?: 0) + 1,
                dateStart = startTimestamp,
                dateFinish = endTimestamp,
                name = name.trim(),
                description = ""
            )

            viewModel.addTask(newTask)
            navController.popBackStack()
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Создать дело")
    }
}
