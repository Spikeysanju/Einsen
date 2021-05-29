package dev.spikeysanju.einsen.view.task

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.spikeysanju.einsen.components.TaskItemCard
import dev.spikeysanju.einsen.components.TopBarWithBack
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.utils.ViewState
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

        viewModel.getAllTask()

        when (val result = viewModel.feed.collectAsState().value) {
            ViewState.Loading -> {
            }
            ViewState.Empty -> {
            }
            is ViewState.Success -> {
                LazyColumn(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                    items(result.task) { task ->
                        TaskItemCard(
                            id = task.id.toString(),
                            title = task.title,
                            emoji = "📮",
                            category = task.category,
                            timer = task.timer.toString()
                        )
                    }
                }
            }
            is ViewState.Error -> {
            }
        }
    }
}