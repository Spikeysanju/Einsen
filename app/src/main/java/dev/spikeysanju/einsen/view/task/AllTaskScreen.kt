package dev.spikeysanju.einsen.view.task

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.spikeysanju.einsen.components.TaskItemCard
import dev.spikeysanju.einsen.components.TopBarWithBack
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel

@Composable
fun AllTaskScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
    actions: MainActions
) {
    Scaffold(topBar = {
        TopBarWithBack(title = "All Task", actions.upPress)
    }) {

        LazyColumn(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
            items(10) {
                TaskItemCard(
                    id = "1",
                    title = "UX Research",
                    emoji = "📮",
                    category = "Open source",
                    timer = "15:00"
                )
            }
        }
    }
}