package dev.spikeysanju.einsen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import dev.spikeysanju.einsen.data.datastore.ThemeManager
import dev.spikeysanju.einsen.navigation.NavGraph
import dev.spikeysanju.einsen.ui.theme.EinsenTheme
import dev.spikeysanju.einsen.ui.theme.myColors
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var themeManager: ThemeManager

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EinsenMain()
        }
        // fetch theme mode
        observeThemeMode()
    }

    @ExperimentalAnimationApi
    @Composable
    fun EinsenMain() {

        // check UI mode
        val darkMode by themeManager.uiModeFlow.collectAsState(initial = isSystemInDarkTheme())

        // set UI mode accordingly
        val toggleTheme: () -> Unit = {
            lifecycleScope.launch {
                themeManager.setDarkMode(!darkMode)
            }
        }

        EinsenTheme(darkTheme = darkMode) {
            Surface(color = myColors.bg) {
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
    val useDarkIcons = true
    val color = colors.background

    SideEffect {
        // Update all of the system bar colors to be transparent, and use
        // dark icons if we're in light theme
        systemUiController.setStatusBarColor(
            color = color,
            darkIcons = useDarkIcons
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EinsenTheme {

    }
}