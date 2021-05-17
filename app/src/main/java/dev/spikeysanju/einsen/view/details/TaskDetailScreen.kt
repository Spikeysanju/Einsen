package dev.spikeysanju.einsen.view.details

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import dev.spikeysanju.einsen.components.TopBarWithBack
import dev.spikeysanju.einsen.navigation.MainActions

@Composable
fun TaskDetailsScreen(navController: NavHostController, action: MainActions, param: Any) {
    Scaffold(topBar = {
        TopBarWithBack(title = "Task Details", action.upPress)
    }) {
        Text(text = "Task Details")
    }
}