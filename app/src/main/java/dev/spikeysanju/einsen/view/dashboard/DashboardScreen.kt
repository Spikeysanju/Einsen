package dev.spikeysanju.einsen.view.dashboard

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.R
import dev.spikeysanju.einsen.components.DashboardCardItem
import dev.spikeysanju.einsen.model.Priority
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.ui.theme.einsenColors
import dev.spikeysanju.einsen.ui.theme.typography
import dev.spikeysanju.einsen.utils.viewstate.ViewState
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel

@Composable
fun DashboardScreen(
    viewModel: MainViewModel,
    actions: MainActions,
    toggleTheme: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.text_my_dashboard),
                        textAlign = TextAlign.Start,
                        style = typography.h5,
                        color = einsenColors.black
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            toggleTheme()
                        }
                    ) {

                        Icon(
                            painter = when (isSystemInDarkTheme()) {
                                true -> painterResource(id = R.drawable.ic_bulb_on)
                                false -> painterResource(id = R.drawable.ic_bulb_off)
                            },
                            contentDescription = stringResource(R.string.text_bulb_turn_on),
                            tint = einsenColors.black
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = {
                            actions.gotoAbout.invoke()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_about),
                            contentDescription = stringResource(R.string.text_bulb_turn_on),
                            tint = einsenColors.black
                        )
                    }
                },
                backgroundColor = einsenColors.background, elevation = 0.dp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(30.dp),
                onClick = {
                    actions.gotoAddTask.invoke()
                },
                backgroundColor = MaterialTheme.colors.onPrimary,
                contentColor = MaterialTheme.colors.background,
                elevation = FloatingActionButtonDefaults.elevation(12.dp)
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.text_addTask),
                    tint = MaterialTheme.colors.onSecondary
                )
            }
        }
    ) {

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
                            color = einsenColors.success,
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
                            color = einsenColors.calm,
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
                            color = einsenColors.err,
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
                            color = einsenColors.warning,
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
