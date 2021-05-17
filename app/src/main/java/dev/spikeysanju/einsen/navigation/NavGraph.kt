package dev.spikeysanju.einsen.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import dev.spikeysanju.einsen.view.HomeScreen
import dev.spikeysanju.einsen.view.add.AddTaskScreen
import dev.spikeysanju.einsen.view.details.TaskDetailsScreen
import dev.spikeysanju.einsen.view.settings.SettingsScreen
import dev.spikeysanju.einsen.view.task.AllTaskScreen
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel

object EndPoints {
    const val ID = "id"
}

@Composable
fun NavGraph(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController) }

    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController, viewModel, actions)
        }

        composable(Screen.AddTask.route) {
            AddTaskScreen(viewModel, actions)
        }

        composable(Screen.AllTask.route) {
            AllTaskScreen(navController, viewModel, actions)
        }

        composable(
            "${Screen.TaskDetails.route}/{id}",
            arguments = listOf(navArgument(EndPoints.ID) { type = NavType.IntType })
        ) {
            TaskDetailsScreen(
                navController,
                actions,
                it.arguments?.getInt(EndPoints.ID) ?: "",
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(viewModel, actions)
        }
    }
}

class MainActions(navController: NavController) {

    val upPress: () -> Unit = {
        navController.navigateUp()
    }

    val gotoSettings: () -> Unit = {
        navController.navigate(Screen.Settings.route)
    }

    val gotoAllTask: () -> Unit = {
        navController.navigate(Screen.AllTask.route)
    }
}