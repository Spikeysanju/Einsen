package dev.spikeysanju.einsen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.spikeysanju.einsen.components.MySlider
import dev.spikeysanju.einsen.ui.theme.EinsenTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EinsenTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = colors.background) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(colors.background)
                    ) {
                        val modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)

                        MySlider()

//                        LazyColumn() {
//                            items(10){
//                                Spacer(modifier = Modifier.height(12.dp))
//                                TaskItemCard(
//                                    id = "1",
//                                    title = "OneFeed App",
//                                    emoji = "📮",
//                                    category = "Open Source",
//                                    timer = "15:00"
//                                )
//                            }
//                        }

//                        Slider()
//                        InputTextField("Title")
//                        PrimaryButton(title = "Save task", onclick = {})
//                        DashboardCardItem(
//                            modifier,
//                            "Important & Urgent",
//                            "12",
//                            colors.primaryVariant
//                        )
//                        DashboardCardItem(modifier, "Important", "03", colors.secondary)
//                        DashboardCardItem(modifier, "Urgent", "08", colors.secondaryVariant)
//                        DashboardCardItem(modifier, "Don't touch!", "01", colors.background)
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