package dev.spikeysanju.einsen.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dev.spikeysanju.einsen.model.task.Priority
import dev.spikeysanju.einsen.view.about.AboutScreen
import dev.spikeysanju.einsen.view.add.AddTaskScreen
import dev.spikeysanju.einsen.view.dashboard.DashboardScreen
import dev.spikeysanju.einsen.view.details.TaskDetailsScreen
import dev.spikeysanju.einsen.view.edit.EditTaskScreen
import dev.spikeysanju.einsen.view.settings.SettingsScreen
import dev.spikeysanju.einsen.view.task.AllTaskScreen
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel
import dev.spikeysanju.einsen.view.webview.WebViewScreen

/**
 * Single source for Navigation Routes of this app.
 */

object EndPoints {
    const val ID = "id"
    const val PRIORITY = "priority"
    const val URL = "url"
    const val TITLE = "title"
}

@Composable
fun NavGraph(toggleTheme: () -> Unit) {
    val navController = rememberAnimatedNavController()

    val actions = remember(navController) { MainActions(navController) }

    AnimatedNavHost(navController, startDestination = Screen.Dashboard.route) {

        /**
         * Navigates to Dashboard.
         */
        composable(
            Screen.Dashboard.route
        ) {
            val viewModel: MainViewModel = viewModel(
                factory = HiltViewModelFactory(LocalContext.current, it)
            )
            viewModel.getAllTask()
            DashboardScreen(viewModel, actions, toggleTheme)
        }


        /**
         * Navigates to Add Notes.
         */
        composable(
            Screen.AddTask.route
        ) {
            val viewModel = hiltViewModel<MainViewModel>(it)
            AddTaskScreen(viewModel, actions)
        }


        /**
         * Navigates to All Task.
         */
        composable(
            "${Screen.AllTask.route}/{priority}",
            arguments = listOf(navArgument(EndPoints.PRIORITY) { type = NavType.StringType })
        ) {
            val viewModel = hiltViewModel<MainViewModel>(it)

            val priority = it.arguments?.getString(EndPoints.PRIORITY)
                ?: throw IllegalStateException("'priority' shouldn't be null")

            viewModel.getTaskByPriority(priority = priority)
            AllTaskScreen(viewModel, actions)
        }


        /**
         * Navigates to Task Details.
         */
        composable(
            "${Screen.TaskDetails.route}/{id}",
            arguments = listOf(navArgument(EndPoints.ID) { type = NavType.IntType })
        ) {
            val viewModel = hiltViewModel<MainViewModel>(it)
            val taskID = it.arguments?.getInt(EndPoints.ID)
                ?: throw IllegalStateException("'task ID' shouldn't be null")

            viewModel.findTaskByID(taskID)
            TaskDetailsScreen(viewModel, actions)
        }


        /**
         * Navigates to Edit Task.
         */
        composable(
            "${Screen.EditTask.route}/{id}",
            arguments = listOf(navArgument(EndPoints.ID) { type = NavType.IntType })
        ) {
            val viewModel = hiltViewModel<MainViewModel>(it)
            val taskID = it.arguments?.getInt(EndPoints.ID)
                ?: throw IllegalStateException("'task ID' shouldn't be null")

            viewModel.findTaskByID(taskID)
            EditTaskScreen(viewModel, actions)
        }


        /**
         * Navigates to Settings.
         */
        composable(Screen.Settings.route) {
            val viewModel = hiltViewModel<MainViewModel>(it)
            SettingsScreen(viewModel, actions)
        }


        /**
         * Navigates to About.
         */
        composable(
            Screen.About.route
        ) {
            val viewModel = hiltViewModel<MainViewModel>(it)
            AboutScreen(viewModel, actions)
        }


        /**
         * Navigates to WebView.
         */
        composable(
            "${Screen.WebView.route}/{title}/{url}",
            arguments = listOf(
                navArgument(EndPoints.TITLE) { type = NavType.StringType },
                navArgument(EndPoints.URL) { type = NavType.StringType }
            )
        ) {
            val viewModel = hiltViewModel<MainViewModel>(it)
            val url = it.arguments?.getString(EndPoints.URL)
                ?: throw IllegalStateException("'URL' shouldn't be null")
            val title = it.arguments?.getString(EndPoints.TITLE)
                ?: throw java.lang.IllegalStateException("'Title' should't be null")
            WebViewScreen(viewModel = viewModel, title = title, url = url, actions = actions)
        }
    }
}


/**
 * A class to define Navigation Route to All Flows of this app/
 */

class MainActions(navController: NavController) {

    val upPress: () -> Unit = {
        navController.navigateUp()
    }

    val gotoAddTask: () -> Unit = {
        navController.navigate(Screen.AddTask.route)
    }

    val gotoAllTask: (priority: Priority) -> Unit = { priority ->
        navController.navigate("${Screen.AllTask.route}/${priority.name}")
    }

    val gotoTaskDetails: (id: Int) -> Unit = { id ->
        navController.navigate("${Screen.TaskDetails.route}/$id")
    }

    val gotoEditTask: (id: Int) -> Unit = { id ->
        navController.navigate("${Screen.EditTask.route}/$id")
    }

    val gotoSettings: () -> Unit = {
        navController.navigate(Screen.Settings.route)
    }

    val gotoAbout: () -> Unit = {
        navController.navigate(Screen.About.route)
    }

    val gotoWebView: (title: String, url: String) -> Unit = { title, url ->
        navController.navigate("${Screen.WebView.route}/$title/$url")
    }
}
