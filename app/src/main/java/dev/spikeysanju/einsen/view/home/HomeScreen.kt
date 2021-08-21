package dev.spikeysanju.einsen.view.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import dev.spikeysanju.einsen.components.DashboardCardItem
import dev.spikeysanju.einsen.components.TopBar
import dev.spikeysanju.einsen.model.Priority
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.ui.theme.myColors
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    actions: MainActions
) {
    Scaffold(topBar = {
        TopBar(title = "My Dashboard")
    }, backgroundColor = myColors.primary) {

        val listState = rememberLazyListState()
        LazyColumn(state = listState) {

            item {
                DashboardCardItem(
                    title = "Do it now",
                    description = "Urgent and Important",
                    count = "07",
                    color = myColors.success,
                    onClick = {
                        actions.gotoAllTask(Priority.URGENT)
                    }
                )

            }

            item {
                DashboardCardItem(
                    title = "Decide when to do",
                    description = "Important Not Urgent",
                    count = "12",
                    color = myColors.calm,
                    onClick = {
                        actions.gotoAllTask(Priority.IMPORTANT)
                    }
                )
            }

            item {
                DashboardCardItem(
                    title = "Delegate it",
                    description = "Urgent Not Important",
                    count = "13",
                    color = myColors.err,
                    onClick = {
                        actions.gotoAllTask(Priority.DELEGATE)
                    }
                )
            }

            item {
                DashboardCardItem(
                    title = "Dump it!",
                    description = "Not Important Not Urgent",
                    count = "04",
                    color = myColors.warning,
                    onClick = {
                        actions.gotoAllTask(Priority.DUMP)
                    }
                )
            }

        }

    }
}