package dev.spikeysanju.einsen.navigation

import androidx.annotation.StringRes
import dev.spikeysanju.einsen.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Home : Screen("home", R.string.text_home)
    object AddTask : Screen("details", R.string.text_addTask)
    object AllTask : Screen("settings", R.string.text_allTask)
    object TaskDetails : Screen("source", R.string.text_taskDetails)
    object Settings : Screen("source_details", R.string.text_settings)
}
