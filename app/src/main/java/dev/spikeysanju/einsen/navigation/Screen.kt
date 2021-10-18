/*
 *
 *  * Copyright 2021 Spikey Sanju
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     https://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

package dev.spikeysanju.einsen.navigation

import androidx.annotation.StringRes
import dev.spikeysanju.einsen.R

/**
 * A sealed class to define all unique navigation routes of this app.
 * @param route
 * @param resourceId
 */
sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Splash : Screen("splash", R.string.text_splash)
    object Dashboard : Screen("dashboard", R.string.text_dashboard)
    object AddTask : Screen("add_task", R.string.text_addTask)
    object EditTask : Screen("edit_task", R.string.text_editTask)
    object AllTask : Screen("all_task", R.string.text_allTask)
    object TaskDetails : Screen("details", R.string.text_taskDetails)
    object About : Screen("about", R.string.text_about)
    object WebView : Screen("webview", R.string.text_webview)
    object AllEmoji : Screen("all_emoji", R.string.text_all_emoji)
}
