package dev.spikeysanju.einsen.view.task

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.R
import dev.spikeysanju.einsen.components.TaskItemCard
import dev.spikeysanju.einsen.components.TopBarWithBack
import dev.spikeysanju.einsen.model.Task
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.utils.ViewState
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel

@Composable
fun AllTaskScreen(
    viewModel: MainViewModel,
    actions: MainActions
) {
    Scaffold(topBar = {
        TopBarWithBack(title = stringResource(R.string.text_allTask), actions.upPress)
    }) {

        viewModel.getAllTask()

        when (val result = viewModel.feed.collectAsState().value) {
            ViewState.Loading -> {
            }
            ViewState.Empty -> {
            }
            is ViewState.Success -> {
                LazyColumn(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                    itemsIndexed(result.task) { index: Int, item: Task ->
                        TaskItemCard(item, onTap = {
                            actions.gotoTaskDetails(item.id)
                        }, onDoubleTap = {
                            viewModel.deleteTaskByID(id = item.id)
                        }, onLongPress = {

                        })
                    }
                }
            }
            is ViewState.Error -> {
            }
        }
    }
}