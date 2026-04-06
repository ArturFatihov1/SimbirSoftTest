package com.example.simbirsofttest.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.simbirsofttest.presentation.TaskViewModel
import com.example.simbirsofttest.presentation.screen.components.CreateTaskButton
import com.example.simbirsofttest.presentation.screen.components.DateField
import com.example.simbirsofttest.presentation.screen.components.TaskDescriptionField
import com.example.simbirsofttest.presentation.screen.components.TaskNameField
import com.example.simbirsofttest.presentation.screen.components.TimeField
import com.example.simbirsofttest.presentation.screen.picker.DatePickerDialogComposable
import com.example.simbirsofttest.presentation.screen.picker.TimePickerDialogComposable
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    viewModel: TaskViewModel,
    navController: NavController
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var startTime by remember { mutableStateOf(LocalTime.of(9, 0)) }
    var endTime by remember { mutableStateOf(LocalTime.of(10, 0)) }

    var showDatePicker by remember { mutableStateOf(false) }
    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Новое дело") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TaskNameField(name = name, onNameChange = { name = it })
            TaskDescriptionField(
                description = description,
                onDescriptionChange = { description = it })

            DateField(
                selectedDate = selectedDate,
                onClick = { showDatePicker = true }
            )

            TimeField(
                label = "Время начала",
                time = startTime,
                onClick = { showStartTimePicker = true }
            )

            TimeField(
                label = "Время окончания",
                time = endTime,
                onClick = { showEndTimePicker = true }
            )

            Spacer(modifier = Modifier.weight(1f))

            CreateTaskButton(
                name = name,
                selectedDate = selectedDate,
                startTime = startTime,
                endTime = endTime,
                viewModel = viewModel,
                navController = navController
            )
        }
    }

    DatePickerDialogComposable(
        show = showDatePicker,
        selectedDate = selectedDate,
        onDateSelected = { selectedDate = it },
        onDismiss = { showDatePicker = false }
    )

    TimePickerDialogComposable(
        show = showStartTimePicker,
        initialTime = startTime,
        onTimeSelected = { startTime = it },
        onDismiss = { showStartTimePicker = false },
        title = "Время начала"
    )

    TimePickerDialogComposable(
        show = showEndTimePicker,
        initialTime = endTime,
        onTimeSelected = { endTime = it },
        onDismiss = { showEndTimePicker = false },
        title = "Время окончания"
    )
}