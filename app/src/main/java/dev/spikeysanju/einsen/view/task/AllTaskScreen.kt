package dev.spikeysanju.einsen.view.task

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun AllTaskScreen(
    viewModel: MainViewModel,
    actions: MainActions
) {
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(sheetState = state, sheetContent = {

        LazyColumn {
            items(50) {
                ListItem(
                    text = { Text("Item $it") },
                    icon = {
                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = "Localized description"
                        )
                    }
                )
            }
        }

    }) {

        Scaffold(topBar = {
            TopBarWithBack(title = stringResource(R.string.text_allTask), actions.upPress)
        }, floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(30.dp),
                onClick = {
                    actions.gotoAddTask.invoke()
                },
                backgroundColor = MaterialTheme.colors.onPrimary,
                contentColor = MaterialTheme.colors.background,
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.text_addTask),
                    tint = MaterialTheme.colors.onBackground
                )
            }
        }) {

            when (val result = viewModel.feed.collectAsState().value) {
                ViewState.Loading -> {
                }
                ViewState.Empty -> {
                }
                is ViewState.Success -> {
                    LazyColumn(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                        itemsIndexed(result.task) { index: Int, item: Task ->
                            TaskItemCard(item,
                                onTap = {
                                    actions.gotoTaskDetails(item.id)
                                },
                                onDoubleTap = {
                                    viewModel.deleteTaskByID(id = item.id)
                                },
                                onLongPress = {
                                    scope.launch {
                                        state.show()
                                    }
                                })
                        }
                    }
                }
                is ViewState.Error -> {

                }
            }
        }

    }


}

