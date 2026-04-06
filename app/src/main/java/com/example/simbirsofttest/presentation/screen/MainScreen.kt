package com.example.simbirsofttest.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.simbirsofttest.R
import com.example.simbirsofttest.presentation.TaskViewModel
import com.example.simbirsofttest.presentation.screen.picker.DatePickerDialogComposable
import org.koin.androidx.compose.koinViewModel
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: TaskViewModel = koinViewModel()
) {
    val selectedDate by viewModel.selectedDate.collectAsState()
    val tasksForDay by viewModel.tasksForDay.collectAsState()
    var showDatePicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.calendar)) }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_task") }) {
                Text("+")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { showDatePicker = true }
                ) {
                    Text(stringResource(R.string.select_date))
                }
            }


            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(24) { hour ->
                    val hourTasks = tasksForDay.filter { it.overlapsWithHour(selectedDate, hour) }
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "${hour.toString().padStart(2, '0')}:00",
                            modifier = Modifier.width(55.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            if (hourTasks.isEmpty()) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(top = 10.dp),
                                    thickness = 0.5.dp
                                )
                            } else {
                                hourTasks.forEach { task ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 4.dp)
                                            .clickable {
                                                navController.navigate("detail/${task.id}")
                                            },
                                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                                    ) {
                                        Text(
                                            task.name,
                                            modifier = Modifier.padding(8.dp),
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    DatePickerDialogComposable(
        show = showDatePicker,
        selectedDate = selectedDate,
        onDateSelected = { viewModel.selectDate(it) },
        onDismiss = { showDatePicker = false }
    )
}