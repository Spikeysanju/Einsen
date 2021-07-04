package dev.spikeysanju.einsen.view.task

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
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

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun AllTaskScreen(
    viewModel: MainViewModel,
    actions: MainActions
) {
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
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
                    tint = MaterialTheme.colors.onSecondary
                )
            }
        }) {

            when (val result = viewModel.feed.collectAsState().value) {
                ViewState.Loading -> {
                }
                ViewState.Empty -> {
                }
                is ViewState.Success -> {
                    LazyColumn(
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            top = 16.dp,
                            end = 16.dp
                        )
                    ) {
                        itemsIndexed(result.task) { index: Int, item: Task ->
                            TaskItemCard(item,
                                onClick = {
                                    actions.gotoTaskDetails(item.id)
                                }, onValueChanged = {
                                    viewModel.updateStatus(item.id, it)
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

