package com.example.simbirsofttest.presentation

import androidx.compose.runtime.Composable
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
    const val DETAIL = "detail"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.MAIN
    ) {
        composable(Routes.MAIN) {
            MainScreen(navController = navController)
        }

        composable(Routes.ADD_TASK) {
            AddTaskScreen(navController = navController)
        }

        composable(
            route = "${Routes.DETAIL}/{taskId}",
            arguments = listOf(
                navArgument("taskId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: -1
            DetailScreen(
                taskId = taskId,
                navController = navController
            )
        }
    }
}