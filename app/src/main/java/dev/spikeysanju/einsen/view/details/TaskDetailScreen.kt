package dev.spikeysanju.einsen.view.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.spikeysanju.einsen.components.InfoCard
import dev.spikeysanju.einsen.components.TopBarWithBack
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.ui.theme.typography

@Composable
fun TaskDetailsScreen(navController: NavHostController, action: MainActions) {
    Scaffold(topBar = {
        TopBarWithBack(title = "Task Details", action.upPress)
    }) {
        LazyColumn(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Open Source",
                    style = typography.subtitle1,
                    textAlign = TextAlign.Start,
                    color = colors.onPrimary
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Your Title here",
                    style = typography.h5,
                    textAlign = TextAlign.Start,
                    color = colors.onPrimary
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "DataStore Part of Android Jetpack. Jetpack DataStore is a data storage solution that allows you to store key-value pairs or typed objects with protocol buffers.\n" +
                            "\n" +
                            "DataStore Part of Android Jetpack. Jetpack DataStore is a data storage solution that allows you to store key-value pairs or typed objects with protocol buffers.\n" +
                            "\n" +
                            "DataStore Part of Android Jetpack. Jetpack DataStore is a data storage solution that allows you to store key-value pairs or typed objects with protocol buffers.\n" +
                            "\n" +
                            "DataStore Part of Android Jetpack. Jetpack DataStore is a data storage solution that allows you to store key-value pairs or typed objects with protocol buffers.",
                    style = typography.body1,
                    textAlign = TextAlign.Start,
                    color = colors.onPrimary
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    InfoCard(title = "Timer", value = "15m")
                    InfoCard(title = "Urgency", value = "05")
                    InfoCard(title = "Priority", value = "03")
                }
            }

        }
    }
}
