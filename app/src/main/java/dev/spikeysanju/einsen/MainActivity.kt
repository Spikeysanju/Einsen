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

package dev.spikeysanju.einsen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import dev.spikeysanju.einsen.data.local.datastore.ThemeManager
import dev.spikeysanju.einsen.navigation.NavGraph
import dev.spikeysanju.einsen.ui.theme.EinsenTheme
import dev.spikeysanju.einsen.ui.theme.einsenColors
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var themeManager: ThemeManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EinsenMain()
        }

        /**
         * Observe the Theme Mode
         */
        observeThemeMode()
    }

    @Composable
    fun EinsenMain() {

        /**
         * Check if the UI Mode is in Dark Mode
         */
        val darkMode by themeManager.uiModeFlow.collectAsState(initial = isSystemInDarkTheme())

        /**
         * Set UI Mode accordingly
         */
        val toggleTheme: () -> Unit = {
            lifecycleScope.launch {
                themeManager.setDarkMode(!darkMode)
            }
        }

        EinsenTheme(darkTheme = darkMode) {
            Surface(color = einsenColors.bg) {
                SetStatusBarColor()
                NavGraph(toggleTheme)
            }
        }
    }

    private fun observeThemeMode() {
        lifecycleScope.launchWhenStarted {
            themeManager.uiModeFlow.collect {
                val mode = when (it) {
                    true -> AppCompatDelegate.MODE_NIGHT_YES
                    false -> AppCompatDelegate.MODE_NIGHT_NO
                }
                AppCompatDelegate.setDefaultNightMode(mode)
            }
        }
    }
}

@Composable
fun SetStatusBarColor() {
    // Remember a SystemUiController
    val systemUiController = rememberSystemUiController()
    val color = einsenColors.bg

    SideEffect {
        /**
         *  Update all of the system bar colors to be transparent, and use
         *  dark icons if we're in light theme
         */
        systemUiController.setStatusBarColor(color = color)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EinsenTheme {
    }
}
