package com.example.simbirsofttest.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.simbirsofttest.presentation.screen.AddTaskScreen
import com.example.simbirsofttest.presentation.screen.DetailScreen
import com.example.simbirsofttest.presentation.screen.MainScreen

object Routes {
    const val MAIN = "main"
    const val ADD_TASK = "add_task"
    const val DETAIL = "detail/{taskId}"
}

object Args {
    const val TASK_ID = "taskId"
}

@Composable
fun AppNavigation(
    viewModel: TaskViewModel = TaskViewModel()
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.MAIN
    ) {
        composable(route = Routes.MAIN) {
            MainScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(route = Routes.ADD_TASK) {
            AddTaskScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(
            route = Routes.DETAIL,
            arguments = listOf(
                navArgument(Args.TASK_ID) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt(Args.TASK_ID) ?: -1

            val task = viewModel.tasksForDay.value.find { it.id == taskId }
                ?: viewModel.allTasks.collectAsState().value.find { it.id == taskId }

            DetailScreen(
                task = task,
                navController = navController
            )
        }
    }
}