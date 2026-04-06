package com.example.simbirsofttest.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.simbirsofttest.R
import com.example.simbirsofttest.data.Task
import com.example.simbirsofttest.presentation.TaskViewModel
import com.example.simbirsofttest.presentation.screen.components.DateField
import com.example.simbirsofttest.presentation.screen.components.TaskDescriptionField
import com.example.simbirsofttest.presentation.screen.components.TaskNameField
import com.example.simbirsofttest.presentation.screen.components.TimeField
import com.example.simbirsofttest.presentation.screen.picker.DatePickerDialogComposable
import com.example.simbirsofttest.presentation.screen.picker.TimePickerDialogComposable
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    navController: NavController,
    viewModel: TaskViewModel = koinViewModel()
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var startTime by remember { mutableStateOf(LocalTime.of(9, 0)) }
    var endTime by remember { mutableStateOf(LocalTime.of(10, 0)) }

    var showDatePicker by remember { mutableStateOf(false) }
    var showStartTime by remember { mutableStateOf(false) }
    var showEndTime by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.new_task)) },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_back_24),
                            contentDescription = "Описание иконки"
                        )
                    }
                })
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            TaskNameField(name) { name = it }
            TaskDescriptionField(description) { description = it }

            Spacer(modifier = Modifier.height(16.dp))

            DateField(selectedDate) { showDatePicker = true }
            TimeField(stringResource(R.string.start), startTime) { showStartTime = true }
            TimeField(stringResource(R.string.finish), endTime) { showEndTime = true }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (name.isNotBlank()) {
                        val start = selectedDate.atTime(startTime).atZone(ZoneId.systemDefault())
                            .toEpochSecond()
                        val end = selectedDate.atTime(endTime).atZone(ZoneId.systemDefault())
                            .toEpochSecond()
                        viewModel.addTask(Task(0, start, end, name, description))
                        navController.popBackStack()
                    }
                }
            ) { Text(stringResource(R.string.save)) }
        }
    }

    DatePickerDialogComposable(
        showDatePicker,
        selectedDate,
        { selectedDate = it },
        { showDatePicker = false })
    TimePickerDialogComposable(
        showStartTime,
        startTime,
        { startTime = it },
        { showStartTime = false },
        stringResource(R.string.start)
    )
    TimePickerDialogComposable(
        showEndTime,
        endTime,
        { endTime = it },
        { showEndTime = false },
        stringResource(R.string.finish)
    )
}