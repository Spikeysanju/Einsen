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

package dev.spikeysanju.einsen.view.edit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.spikeysanju.einsen.R
import dev.spikeysanju.einsen.components.EinsenInputTextField
import dev.spikeysanju.einsen.components.EinsenStepSlider
import dev.spikeysanju.einsen.components.EmojiPlaceHolder
import dev.spikeysanju.einsen.components.PrimaryButton
import dev.spikeysanju.einsen.model.task.Priority
import dev.spikeysanju.einsen.model.task.task
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.ui.theme.Sailec
import dev.spikeysanju.einsen.ui.theme.einsenColors
import dev.spikeysanju.einsen.ui.theme.typography
import dev.spikeysanju.einsen.utils.calculatePriority
import dev.spikeysanju.einsen.utils.showToast
import dev.spikeysanju.einsen.utils.viewstate.SingleViewState
import dev.spikeysanju.einsen.view.animationviewstate.AnimationViewState
import dev.spikeysanju.einsen.view.animationviewstate.ScreenState
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun EditTaskScreen(modifier: Modifier, viewModel: MainViewModel, actions: MainActions) {

    // Coroutines scope
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // slider points
    val points = listOf("0", "1", "2", "3", "4")

    // List and bottom sheet state
    val listState = rememberLazyListState()

    // All Task State
    var taskID by rememberSaveable { mutableStateOf(0) }
    var titleState by rememberSaveable { mutableStateOf("") }
    var descriptionState by rememberSaveable { mutableStateOf("") }
    var categoryState by remember { mutableStateOf("") }
    var emojiState by remember { mutableStateOf("") }
    var urgencyState by remember { mutableStateOf(0) }
    var importanceState by remember { mutableStateOf(0) }
    var dueState by remember { mutableStateOf("18/12/1998") }
    var priorityState by remember { mutableStateOf(Priority.IMPORTANT) }
    var isCompletedState by remember { mutableStateOf(false) }
    var createdAtState by remember { mutableStateOf(0L) }
    val updatedAtState by remember { mutableStateOf(System.currentTimeMillis()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.text_editTask),
                        style = typography.h6,
                        textAlign = TextAlign.Start,
                        color = einsenColors.black,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { actions.upPress.invoke() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = stringResource(R.string.back_button),
                            tint = einsenColors.black
                        )
                    }
                },
                backgroundColor = einsenColors.background, elevation = 0.dp
            )
        }
    ) {

        when (val taskResult = viewModel.singleTask.collectAsState().value) {
            SingleViewState.Empty -> {
                AnimationViewState(
                    modifier,
                    title = stringResource(R.string.text_no_task_title),
                    description = stringResource(R.string.text_no_task_description),
                    callToAction = stringResource(R.string.text_add_a_task),
                    ScreenState.EMPTY,
                    actions = {
                        actions.gotoAddTask.invoke(0, 0)
                    }
                )
            }
            is SingleViewState.Error -> {
                AnimationViewState(
                    modifier,
                    title = stringResource(R.string.text_error_title),
                    description = stringResource(
                        R.string.text_error_description
                    ),
                    callToAction = stringResource(R.string.text_add_a_task),
                    ScreenState.ERROR,
                    actions = {
                        actions.gotoAddTask.invoke(0, 0)
                    }
                )
            }
            SingleViewState.Loading -> {
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
            is SingleViewState.Success -> {

                // update the task state with latest value

                with(taskResult.task) {
                    taskID = this.id
                    titleState = this.title
                    descriptionState = this.description
                    categoryState = this.category
                    emojiState = this.emoji
                    urgencyState = this.urgency
                    importanceState = this.importance
                    priorityState = this.priority
                    dueState = this.due
                    isCompletedState = this.isCompleted
                    createdAtState = this.createdAt
                }

                /*
                Check if the current emoji from backstack is empty or not.
                If it's empty then set the emoji to the task emoji or else set selected emoji to
                the [EmojiState]
                 */
                val currentEmoji = viewModel.currentEmoji.collectAsState().value
                emojiState = if (currentEmoji.isEmpty()) {
                    taskResult.task.emoji
                } else {
                    currentEmoji
                }

                LazyColumn(state = listState, contentPadding = PaddingValues(bottom = 24.dp)) {

                    // Emoji
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            EmojiPlaceHolder(
                                emoji = emojiState,
                                onSelect = {
                                    scope.launch {
                                        actions.gotoAllEmoji.invoke()
                                    }
                                }
                            )
                        }
                    }

                    // Title
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        EinsenInputTextField(
                            title = stringResource(R.string.text_title),
                            value = titleState
                        ) {
                            titleState = it
                        }
                    }

                    // Description
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        EinsenInputTextField(
                            title = stringResource(R.string.text_description),
                            value = descriptionState
                        ) {
                            descriptionState = it
                        }
                    }

                    // Category
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        EinsenInputTextField(
                            title = stringResource(R.string.text_category),
                            value = categoryState
                        ) {
                            categoryState = it
                        }
                    }

                    val titleStyle = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = Sailec,
                        fontWeight = FontWeight.Bold
                    )

                    // Urgency
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp)) {
                            Text(
                                text = stringResource(R.string.text_urgency),
                                style = titleStyle,
                                color = MaterialTheme.colors.onPrimary
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            EinsenStepSlider(modifier, points, urgencyState.toFloat()) {
                                urgencyState = it
                            }
                        }
                    }

                    // Importance
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp)) {
                            Text(
                                text = stringResource(R.string.text_importance),
                                style = titleStyle,
                                color = MaterialTheme.colors.onPrimary
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            EinsenStepSlider(modifier, points, importanceState.toFloat()) {
                                importanceState = it
                            }
                        }
                    }

                    // Update Task CTA
                    item {

                        Spacer(modifier = Modifier.height(36.dp))
                        PrimaryButton(title = stringResource(R.string.text_save_task)) {

                            // calculate the average value by adding urgency + priority / 2
                            val priorityAverage = importanceState + urgencyState / 2
                            priorityState = calculatePriority(priorityAverage)

                            val task = task {
                                title = titleState
                                description = descriptionState
                                category = categoryState
                                emoji = emojiState
                                urgency = urgencyState
                                importance = importanceState
                                priority = priorityState
                                due = dueState
                                isCompleted = isCompletedState
                                createdAt = createdAtState
                                updatedAt = updatedAtState
                                id = taskID
                            }

                            when {
                                titleState.isEmpty() && descriptionState.isEmpty() || categoryState.isEmpty() -> {
                                    showToast(context, "Please fill all the fields & save the task")
                                }
                                else -> {
                                    viewModel.insertTask(task).run {
                                        showToast(context, "Task updated successfully!")
                                        actions.upPress.invoke()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun BottomSheetTitle() {
    Text(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 24.dp),
        text = stringResource(R.string.tetxt_choose_emoji),
        style = typography.h5,
        textAlign = TextAlign.Start,
        color = MaterialTheme.colors.onPrimary
    )
}
