package dev.spikeysanju.einsen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.spikeysanju.einsen.navigation.NavGraph
import dev.spikeysanju.einsen.ui.theme.EinsenTheme
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EinsenTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = colors.background) {
                    val viewModel: MainViewModel = viewModel()
                    NavGraph(viewModel = viewModel)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EinsenTheme {

    }
}