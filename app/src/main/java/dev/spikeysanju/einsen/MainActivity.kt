package dev.spikeysanju.einsen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.spikeysanju.einsen.components.DashboardCardItem
import dev.spikeysanju.einsen.components.InputTextField
import dev.spikeysanju.einsen.components.PrimaryButton
import dev.spikeysanju.einsen.components.Slider
import dev.spikeysanju.einsen.ui.theme.EinsenTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EinsenTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column {
                        val modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)

                        Slider()
                        InputTextField("Title")
                        PrimaryButton(title = "Save task", onclick = {})
                        DashboardCardItem(
                            modifier,
                            "Important & Urgent",
                            "12",
                            colors.primaryVariant
                        )
                        DashboardCardItem(modifier, "Important", "03", colors.secondary)
                        DashboardCardItem(modifier, "Urgent", "08", colors.secondaryVariant)
                        DashboardCardItem(modifier, "Don't touch!", "01", colors.background)
                    }
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