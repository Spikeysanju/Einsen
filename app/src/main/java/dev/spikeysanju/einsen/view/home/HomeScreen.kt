package dev.spikeysanju.einsen.view.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import dev.spikeysanju.einsen.R
import dev.spikeysanju.einsen.components.DashboardCardItem
import dev.spikeysanju.einsen.components.TopBar
import dev.spikeysanju.einsen.model.Priority
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.ui.theme.myColors
import dev.spikeysanju.einsen.utils.ViewState
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    actions: MainActions
) {
    Scaffold(topBar = {
        TopBar(title = stringResource(R.string.text_my_dashboard))
    }, backgroundColor = myColors.primary) {

        val listState = rememberLazyListState()
        var urgentCount by remember { mutableStateOf(0) }
        var importantCount by remember { mutableStateOf(0) }
        var delegateCount by remember { mutableStateOf(0) }
        var dumpCount by remember { mutableStateOf(0) }

        when (val allTaskList = viewModel.feed.collectAsState().value) {
            ViewState.Empty -> {
            }
            ViewState.Loading -> {
            }
            is ViewState.Success -> {

                // this will split list into urgent on left & important on right
                val (urgent, important) = allTaskList.task.partition { it.priority.name == Priority.URGENT.name }

                // this will split list into urgent on left & important on right
                val (delegate, dump) = allTaskList.task.partition { it.priority.name == Priority.DELEGATE.name }

                // setting task count to mutable state
                urgentCount = urgent.count { it.priority.name == Priority.URGENT.name }
                importantCount = important.count { it.priority.name == Priority.IMPORTANT.name }
                delegateCount = delegate.count { it.priority.name == Priority.DELEGATE.name }
                dumpCount = dump.count { it.priority.name == Priority.DUMP.name }

                LazyColumn(state = listState) {

                    item {
                        DashboardCardItem(
                            title = stringResource(R.string.text_do_it_now),
                            description = stringResource(R.string.text_important_and_urgent),
                            count = urgentCount.toString(),
                            color = myColors.success,
                            onClick = {
                                actions.gotoAllTask(Priority.URGENT)
                            }
                        )

                    }

                    item {
                        DashboardCardItem(
                            title = stringResource(R.string.text_decide_when_todo),
                            description = stringResource(R.string.text_important_not_urgent),
                            count = importantCount.toString(),
                            color = myColors.calm,
                            onClick = {
                                actions.gotoAllTask(Priority.IMPORTANT)
                            }
                        )
                    }

                    item {
                        DashboardCardItem(
                            title = stringResource(R.string.text_delegate_it),
                            description = stringResource(R.string.text_urgent_not_important),
                            count = delegateCount.toString(),
                            color = myColors.err,
                            onClick = {
                                actions.gotoAllTask(Priority.DELEGATE)
                            }
                        )
                    }

                    item {
                        DashboardCardItem(
                            title = stringResource(R.string.text_dump_it),
                            description = stringResource(R.string.text_not_important_not_urgent),
                            count = dumpCount.toString(),
                            color = myColors.warning,
                            onClick = {
                                actions.gotoAllTask(Priority.DUMP)
                            }
                        )
                    }
                }
            }
            is ViewState.Error -> {
                // Todo show error
            }
        }
    }
}