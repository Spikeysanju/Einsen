package dev.spikeysanju.einsen.view

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
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
        Text(text = "Home")
    }
}