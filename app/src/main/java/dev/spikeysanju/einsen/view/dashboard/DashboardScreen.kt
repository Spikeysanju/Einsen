/*
 *
 *  * Copyright 2021 Spikey Sanju
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     https://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

package dev.spikeysanju.einsen.view.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import dev.spikeysanju.einsen.R
import dev.spikeysanju.einsen.components.DashboardCardItem
import dev.spikeysanju.einsen.model.task.Priority
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.ui.theme.einsenColors
import dev.spikeysanju.einsen.ui.theme.typography
import dev.spikeysanju.einsen.utils.viewstate.ViewState
import dev.spikeysanju.einsen.view.animationviewstate.AnimationViewState
import dev.spikeysanju.einsen.view.animationviewstate.ScreenState
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel

@Composable
fun DashboardScreen(
    modifier: Modifier,
    viewModel: MainViewModel,
    actions: MainActions,
    toggleTheme: () -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        // log event to firebase
        val dashboardScreenComposable = bundleOf(
            FirebaseAnalytics.Param.SCREEN_NAME to "Dashboard Screen",
            FirebaseAnalytics.Param.SCREEN_CLASS to "DashboardScreen.kt"
        )

        viewModel.firebaseLogEvent("dashboard_screen", dashboardScreenComposable)
    }

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

                    // log event to firebase
                    val bundle = bundleOf(
                        "theme_switch" to isSystemInDarkTheme()
                    )

                    IconButton(
                        onClick = {
                            toggleTheme().run {
                                viewModel.firebaseLogEvent("dashboard_theme_switch", bundle)
                            }
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

                    Spacer(modifier = modifier.width(8.dp))

                    IconButton(
                        onClick = {
                            actions.gotoAbout.invoke().run {
                                // log event to firebase
                                val aboutBundle = bundleOf(
                                    "about_button" to "Clicked about button from Dashboard"
                                )
                                viewModel.firebaseLogEvent("dashboard_about_button", aboutBundle)
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_about),
                            contentDescription = stringResource(R.string.text_bulb_turn_on),
                            tint = einsenColors.black
                        )
                    }
                }, backgroundColor = einsenColors.bg, elevation = 0.dp
            )
        },
        floatingActionButton = {

            FloatingActionButton(
                modifier = modifier.padding(30.dp),
                onClick = {
                    actions.gotoAddTask.invoke(0, 0).run {
                        // log event to firebase
                        val addTaskBundle = bundleOf(
                            "add_task" to "Clicked Add Task button from Dashboard"
                        )

                        viewModel.firebaseLogEvent("dashboard_add_task_button", addTaskBundle)
                    }
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
                AnimationViewState(
                    modifier,
                    title = stringResource(R.string.text_no_task_title),
                    description = stringResource(R.string.text_no_task_description),
                    callToAction = stringResource(R.string.text_add_a_task),
                    ScreenState.EMPTY,
                    actions = {
                        actions.gotoAddTask.invoke(0, 0).run {
                            // log event to firebase
                            val emptyStateCTAButton = bundleOf(
                                "empty_state_add_task" to "Clicked empty state Add Task button from Dashboard"
                            )

                            viewModel.firebaseLogEvent(
                                "dashboard_empty_state_add_task_button",
                                emptyStateCTAButton
                            )
                        }
                    }
                )
            }
            ViewState.Loading -> {
                AnimationViewState(
                    modifier,
                    title = stringResource(R.string.text_no_task_title),
                    description = stringResource(R.string.text_no_task_description),
                    callToAction = stringResource(R.string.text_add_a_task),
                    ScreenState.LOADING,
                    actions = {
                        actions.gotoAddTask.invoke(0, 0)
                    }
                )
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

                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(bottom = 100.dp),
                    modifier = modifier
                        .background(
                            einsenColors.bg
                        )
                        .fillMaxSize()
                ) {

                    item {
                        DashboardCardItem(
                            title = stringResource(R.string.text_do_it_now),
                            description = stringResource(R.string.text_important_and_urgent),
                            count = urgentCount.toString(),
                            color = einsenColors.success,
                            onClick = {
                                actions.gotoAllTask(Priority.URGENT).run {
                                    // log event to firebase
                                    val priorityUrgentBundle = bundleOf(
                                        "priority_urgent" to "Clicked Priority Urgent from the Dashboard"
                                    )

                                    viewModel.firebaseLogEvent(
                                        "dashboard_priority_urgent",
                                        priorityUrgentBundle
                                    )
                                }
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
                                actions.gotoAllTask(Priority.IMPORTANT).run {
                                    // log event to firebase
                                    val priorityImportantBundle = bundleOf(
                                        "priority_important" to "Clicked Priority Important from the Dashboard"
                                    )

                                    viewModel.firebaseLogEvent(
                                        "dashboard_priority_important",
                                        priorityImportantBundle
                                    )
                                }
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
                                actions.gotoAllTask(Priority.DELEGATE).run {
                                    // log event to firebase
                                    val priorityDelegateBundle = bundleOf(
                                        "priority_delegate" to "Clicked Priority Delegate from the Dashboard"
                                    )

                                    viewModel.firebaseLogEvent(
                                        "dashboard_priority_delegate",
                                        priorityDelegateBundle
                                    )
                                }
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
                                actions.gotoAllTask(Priority.DUMP).run {
                                    // log event to firebase
                                    val priorityDumpItBundle = bundleOf(
                                        "priority_dump_it" to "Clicked Priority Dump it from the Dashboard"
                                    )

                                    viewModel.firebaseLogEvent(
                                        "dashboard_priority_dump_it",
                                        priorityDumpItBundle
                                    )
                                }
                            }
                        )
                    }
                }
            }
            is ViewState.Error -> {
                AnimationViewState(
                    modifier,
                    title = stringResource(R.string.text_error_title),
                    description = stringResource(
                        R.string.text_error_description
                    ),
                    callToAction = stringResource(R.string.text_add_a_task),
                    ScreenState.ERROR,
                    actions = {
                        actions.gotoAddTask.invoke(0, 0).run {
                            // log event to firebase
                            val errorBundle = bundleOf(
                                "priority_error" to "${allTaskList.exception}"
                            )

                            viewModel.firebaseLogEvent("dashboard_priority_error", errorBundle)
                        }
                    }
                )
            }
        }
    }
}
