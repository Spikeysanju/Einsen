package dev.spikeysanju.einsen.navigation

import androidx.annotation.StringRes
import dev.spikeysanju.einsen.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Home : Screen("home", R.string.text_home)
    object AddTask : Screen("add_task", R.string.text_addTask)
    object EditTask : Screen("edit_task", R.string.text_editTask)
    object AllTask : Screen("all_task", R.string.text_allTask)
    object TaskDetails : Screen("details", R.string.text_taskDetails)
    object Settings : Screen("settings", R.string.text_settings)
}
