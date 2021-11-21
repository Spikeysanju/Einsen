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

package dev.spikeysanju.einsen.view.add

import androidx.compose.foundation.clickable
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
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import dev.spikeysanju.einsen.R
import dev.spikeysanju.einsen.components.EinsenInputTextField
import dev.spikeysanju.einsen.components.EinsenStepSlider
import dev.spikeysanju.einsen.components.EmojiPlaceHolder
import dev.spikeysanju.einsen.components.PrimaryButton
import dev.spikeysanju.einsen.components.showDatePicker
import dev.spikeysanju.einsen.components.showTimePicker
import dev.spikeysanju.einsen.model.task.Priority
import dev.spikeysanju.einsen.model.task.task
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.ui.theme.Sailec
import dev.spikeysanju.einsen.ui.theme.einsenColors
import dev.spikeysanju.einsen.ui.theme.typography
import dev.spikeysanju.einsen.utils.calculatePriority
import dev.spikeysanju.einsen.utils.formatCalendar
import dev.spikeysanju.einsen.utils.getCalendar
import dev.spikeysanju.einsen.utils.showToast
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel
import dev.spikeysanju.einsen.workers.scheduleReminders
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun AddTaskScreen(
    modifier: Modifier,
    viewModel: MainViewModel,
    actions: MainActions,
    defaultUrgency: Int,
    defaultImportance: Int
) {

    // component state
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()

    // slider points
    val points = listOf("0", "1", "2", "3", "4")

    // task value state
    var taskState by remember {
        mutableStateOf(
            task {
                title = ""
                description = ""
                category = ""
                emoji = ""
                urgency = defaultUrgency
                importance = defaultImportance
                priority = Priority.IMPORTANT
                due = ""
                isCompleted = false
            }
        )
    }

    LaunchedEffect(
        key1 = Unit,
        block = {
            // log event to firebase
            val addTaskComposable = bundleOf(
                FirebaseAnalytics.Param.SCREEN_NAME to "Add Task Screen",
                FirebaseAnalytics.Param.SCREEN_CLASS to "AddTaskScreen.kt"
            )

            viewModel.firebaseLogEvent("add_task_screen", addTaskComposable)
        }
    )

    // get current emoji
    viewModel.currentEmoji.collectAsState().value.apply {
        taskState = taskState.copy(emoji = this)
    }

    // Add Task Screen content
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.text_addTask),
                        style = typography.h6,
                        textAlign = TextAlign.Start,
                        color = einsenColors.black,
                        modifier = modifier.padding(start = 16.dp)
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

        LazyColumn(state = listState, contentPadding = PaddingValues(bottom = 24.dp)) {

            // Emoji
            item {
                Spacer(modifier = modifier.height(24.dp))
                Box(
                    modifier = modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    EmojiPlaceHolder(
                        emoji = taskState.emoji,
                        onSelect = {
                            scope.launch {
                                actions.gotoAllEmoji.invoke().run {
                                    // log event to firebase
                                    val emojiBundle = bundleOf(
                                        "all_emoji_bottom_sheet" to "Clicked All Emoji placeholder to open Emoji BottomSheet"
                                    )

                                    viewModel.firebaseLogEvent("emoji_bottom_sheet", emojiBundle)
                                }
                            }
                        }
                    )
                }
            }

            // Title
            item {
                Spacer(modifier = modifier.height(24.dp))
                EinsenInputTextField(
                    title = stringResource(R.string.text_title),
                    value = taskState.title
                ) {
                    taskState = taskState.copy(title = it)
                }
            }

            // Description
            item {
                Spacer(modifier = modifier.height(24.dp))
                EinsenInputTextField(
                    title = stringResource(R.string.text_description),
                    value = taskState.description
                ) {
                    taskState = taskState.copy(description = it)
                }
            }

            // Category
            item {
                Spacer(modifier = modifier.height(24.dp))
                EinsenInputTextField(
                    title = stringResource(R.string.text_category),
                    value = taskState.category
                ) {
                    taskState = taskState.copy(category = it)
                }
            }

            // Due Date Time
            item {
                Spacer(modifier = modifier.height(24.dp))
                EinsenInputTextField(
                    modifier = Modifier.clickable {
                        val calendar = getCalendar(taskState.due)
                        context.showDatePicker(calendar) {
                            calendar.set(Calendar.DAY_OF_MONTH, it.get(Calendar.DAY_OF_MONTH))
                            calendar.set(Calendar.MONTH, it.get(Calendar.MONTH))
                            calendar.set(Calendar.YEAR, it.get(Calendar.YEAR))
                            context.showTimePicker(calendar) { timeCalendar ->
                                taskState = taskState.copy(due = formatCalendar(timeCalendar))
                            }
                        }
                    },
                    title = stringResource(R.string.text_due_date_time),
                    value = taskState.due,
                    readOnly = true, enabled = false, {}
                )
            }

            val titleStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = Sailec,
                fontWeight = FontWeight.Bold
            )

            // Urgency
            item {
                Spacer(modifier = modifier.height(24.dp))
                Column(modifier = modifier.padding(start = 24.dp, end = 24.dp)) {
                    Text(
                        text = stringResource(R.string.text_urgency),
                        style = titleStyle,
                        color = MaterialTheme.colors.onPrimary
                    )
                    Spacer(modifier = modifier.height(12.dp))

                    EinsenStepSlider(modifier, points, taskState.urgency.toFloat()) {
                        taskState = taskState.copy(urgency = it)
                    }
                }
            }

            // Importance
            item {
                Spacer(modifier = modifier.height(24.dp))
                Column(modifier = modifier.padding(start = 24.dp, end = 24.dp)) {
                    Text(
                        text = stringResource(R.string.text_importance),
                        style = titleStyle,
                        color = MaterialTheme.colors.onPrimary
                    )
                    Spacer(modifier = modifier.height(12.dp))

                    EinsenStepSlider(modifier, points, taskState.importance.toFloat()) {
                        taskState = taskState.copy(importance = it)
                    }
                }
            }

            // Save Task CTA
            item {
                Spacer(modifier = modifier.height(36.dp))
                PrimaryButton(title = stringResource(R.string.text_save_task)) {

                    // calculate the average value by adding urgency + priority / 2
                    val priorityAverage = taskState.importance + taskState.urgency / 2
                    taskState = taskState.copy(priority = calculatePriority(priorityAverage))

                    when {
                        taskState.title.isEmpty() && taskState.description.isEmpty() || taskState.category.isEmpty() -> {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(message = "Please fill all the fields & save the task")
                            }
                        }
                        else -> {
                            viewModel.insertTask(taskState).run {
                                context.scheduleReminders(taskState)
                                showToast(context, "Task added successfully")

                                // log event to firebase
                                val addTaskBundle = bundleOf(
                                    "add_task_button" to "Clicked Add Task button to save the new task"
                                )

                                viewModel.firebaseLogEvent("add_task_save_button", addTaskBundle)

                                actions.upPress.invoke()
                            }
                        }
                    }
                }
            }
        }
    }
}
