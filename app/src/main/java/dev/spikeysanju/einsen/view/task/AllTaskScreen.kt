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

package dev.spikeysanju.einsen.view.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import dev.spikeysanju.einsen.R
import dev.spikeysanju.einsen.components.TaskItemCard
import dev.spikeysanju.einsen.model.task.Task
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.ui.theme.apptheme.AppTheme
import dev.spikeysanju.einsen.utils.viewstate.ViewState
import dev.spikeysanju.einsen.view.animationviewstate.AnimationViewState
import dev.spikeysanju.einsen.view.animationviewstate.ScreenState
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel

@Composable
fun AllTaskScreen(
    modifier: Modifier,
    viewModel: MainViewModel,
    actions: MainActions,
    defaultUrgency: Int = 0,
    defaultImportance: Int = 0
) {

    LaunchedEffect(key1 = Unit) {
        // log event to firebase
        val allTaskScreenComposable = bundleOf(
            FirebaseAnalytics.Param.SCREEN_NAME to "All Task Screen",
            FirebaseAnalytics.Param.SCREEN_CLASS to "AllTaskScreen.kt"
        )

        viewModel.firebaseLogEvent("all_task_screen", allTaskScreenComposable)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.text_allTask),
                        style = AppTheme.typography.h2,
                        textAlign = TextAlign.Start,
                        color = AppTheme.colors.text,
                        modifier = modifier.padding(start = 16.dp),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { actions.upPress.invoke() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = stringResource(R.string.back_button),
                            tint = AppTheme.colors.primary
                        )
                    }
                },
                backgroundColor = AppTheme.colors.background, elevation = 0.dp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = modifier.padding(30.dp),
                onClick = {
                    actions.gotoAddTask.invoke(defaultUrgency, defaultImportance).run {
                        // log event to firebase
                        val aboutBundle = bundleOf(
                            "add_button" to "Clicked Add Task button from All Task"
                        )
                        viewModel.firebaseLogEvent("all_task_add_button", aboutBundle)
                    }
                },
                backgroundColor = AppTheme.colors.primary,
                contentColor = AppTheme.colors.text,
                elevation = FloatingActionButtonDefaults.elevation(12.dp)
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.text_addTask),
                    tint = AppTheme.colors.white
                )
            }
        },
        modifier = modifier.background(AppTheme.colors.background)
    ) {

        when (val result = viewModel.feed.collectAsState().value) {
            ViewState.Loading -> {
                AnimationViewState(
                    modifier,
                    title = stringResource(R.string.text_no_task_title),
                    description = stringResource(R.string.text_no_task_description),
                    callToAction = stringResource(R.string.text_add_a_task),
                    ScreenState.LOADING,
                    actions = {
                        actions.gotoAddTask.invoke(defaultUrgency, defaultImportance)
                    }
                )
            }
            ViewState.Empty -> {
                AnimationViewState(
                    modifier,
                    title = stringResource(R.string.text_no_task_title),
                    description = stringResource(R.string.text_no_task_description),
                    callToAction = stringResource(R.string.text_add_a_task),
                    ScreenState.EMPTY,
                    actions = {
                        actions.gotoAddTask.invoke(defaultUrgency, defaultImportance).run {
                            // log event to firebase
                            val emptyStateCTAButton = bundleOf(
                                "empty_state_add_task" to "Clicked empty state Add Task button from All Task"
                            )

                            viewModel.firebaseLogEvent(
                                "all_task_empty_state_add_task_button",
                                emptyStateCTAButton
                            )
                        }
                    }
                )
            }
            is ViewState.Success -> {
                LazyColumn(
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp
                    ),
                    modifier = modifier
                        .fillMaxSize()
                        .background(AppTheme.colors.background)
                ) {
                    itemsIndexed(result.task) { index: Int, item: Task ->
                        TaskItemCard(
                            modifier,
                            item,
                            onClick = {
                                actions.gotoTaskDetails(item.id).run {
                                    // log event to firebase
                                    val aboutBundle = bundleOf(
                                        "task_item_card" to "Clicked Task Item Card from All Task"
                                    )
                                    viewModel.firebaseLogEvent(
                                        "all_task_task_item_card",
                                        aboutBundle
                                    )
                                }
                            },
                            onCheckboxChange = {
                                viewModel.updateStatus(item.id, it)
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
                        actions.gotoAddTask.invoke(defaultUrgency, defaultImportance).run {
                            // log event to firebase
                            val errorBundle = bundleOf(
                                "all_task_error" to "${result.exception}"
                            )

                            viewModel.firebaseLogEvent(
                                "all_task_error",
                                errorBundle
                            )
                        }
                    }
                )
            }
        }
    }
}
