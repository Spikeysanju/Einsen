package dev.spikeysanju.einsen.view.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import dev.spikeysanju.einsen.components.TaskItemCard
import dev.spikeysanju.einsen.components.TopBar
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
    actions: MainActions
) {
    Scaffold(topBar = {
        TopBar(title = "Home")
    }) {
        LazyColumn {
            items(12) {
                TaskItemCard(
                    id = "1",
                    title = "Einsen Architecture",
                    emoji = "😍",
                    category = "Open source",
                    timer = "12:40"
                )
            }
        }
    }
}