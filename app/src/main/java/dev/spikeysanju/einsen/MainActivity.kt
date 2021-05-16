package dev.spikeysanju.einsen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.spikeysanju.einsen.components.DashboardCardItem
import dev.spikeysanju.einsen.components.InputTextField
import dev.spikeysanju.einsen.components.PrimaryButton
import dev.spikeysanju.einsen.components.Slider
import dev.spikeysanju.einsen.ui.theme.EinsenTheme
import dev.spikeysanju.einsen.ui.theme.blue200
import dev.spikeysanju.einsen.ui.theme.blue300
import dev.spikeysanju.einsen.ui.theme.blue400
import dev.spikeysanju.einsen.ui.theme.blue500


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
                        DashboardCardItem(modifier, "Important & Urgent", "12", blue200)
                        DashboardCardItem(modifier, "Important", "03", blue300)
                        DashboardCardItem(modifier, "Urgent", "08", blue400)
                        DashboardCardItem(modifier, "Don't touch!", "01", blue500)
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