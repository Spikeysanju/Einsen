package dev.spikeysanju.einsen.view.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dev.spikeysanju.einsen.components.DashboardCardItem
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
        TopBar(title = "Dashboard")
    }) {

        Column {
            val modifier = Modifier
                .fillMaxWidth()
                .weight(1f)

            DashboardCardItem(
                modifier = modifier,
                title = "Important & Urgent",
                count = "07",
                color = colors.primary
            )

            DashboardCardItem(
                modifier = modifier,
                title = "Important",
                count = "12",
                color = colors.primaryVariant
            )

            DashboardCardItem(
                modifier = modifier,
                title = "Urgent",
                count = "13",
                color = colors.secondary
            )

            DashboardCardItem(
                modifier = modifier,
                title = "Dont't touch!",
                count = "04",
                color = colors.secondaryVariant
            )
        }

    }
}