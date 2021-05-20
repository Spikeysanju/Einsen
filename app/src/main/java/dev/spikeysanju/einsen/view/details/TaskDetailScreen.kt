package dev.spikeysanju.einsen.view.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import dev.spikeysanju.einsen.components.TopBarWithBack
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.ui.theme.typography

@Composable
fun TaskDetailsScreen(navController: NavHostController, action: MainActions, param: Any) {
    Scaffold(topBar = {
        TopBarWithBack(title = "Task Details", action.upPress)
    }) {
        LazyColumn {

            item {
                Text(text = "Open Source")
            }

        }
    }
}

@Composable
fun DetailsTextView() {
    Column {
        Text(
            text = "Open Source",
            style = typography.subtitle1,
            textAlign = TextAlign.Start,
            color = colors.onPrimary
        )
        Text(
            text = "Your Title here",
            style = typography.h5,
            textAlign = TextAlign.Start,
            color = colors.onPrimary
        )
        Text(
            text = "Your description goes here...",
            style = typography.body1,
            textAlign = TextAlign.Start,
            color = colors.onPrimary
        )
    }
}