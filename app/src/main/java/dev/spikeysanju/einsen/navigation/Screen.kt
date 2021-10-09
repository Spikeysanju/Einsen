package dev.spikeysanju.einsen.navigation

import androidx.annotation.StringRes
import dev.spikeysanju.einsen.R

/**
 * A sealed class to define all unique navigation routes of this app.
 * @param route
 * @param resourceId
 */
sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Dashboard : Screen("dashboard", R.string.text_dashboard)
    object AddTask : Screen("add_task", R.string.text_addTask)
    object EditTask : Screen("edit_task", R.string.text_editTask)
    object AllTask : Screen("all_task", R.string.text_allTask)
    object TaskDetails : Screen("details", R.string.text_taskDetails)
    object About : Screen("about", R.string.text_about)
    object WebView : Screen("webview", R.string.text_webview)
    object AllEmoji : Screen("all_emoji", R.string.text_all_emoji)
}
