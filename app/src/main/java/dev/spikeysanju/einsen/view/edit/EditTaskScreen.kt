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

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
import dev.spikeysanju.einsen.ui.theme.apptheme.AppTheme
import dev.spikeysanju.einsen.utils.calculatePriority
import dev.spikeysanju.einsen.utils.formatCalendar
import dev.spikeysanju.einsen.utils.getCalendar
import dev.spikeysanju.einsen.utils.showToast
import dev.spikeysanju.einsen.utils.viewstate.SingleViewState
import dev.spikeysanju.einsen.view.animationviewstate.AnimationViewState
import dev.spikeysanju.einsen.view.animationviewstate.ScreenState
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel
import dev.spikeysanju.einsen.workers.scheduleReminders
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun EditTaskScreen(modifier: Modifier, viewModel: MainViewModel, actions: MainActions) {

    // Coroutines scope
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // slider points
    val points = listOf("0", "1", "2", "3", "4")

    // List, Scaffold and bottom sheet state
    val listState = rememberLazyListState()
    val scaffoldState = rememberScaffoldState()

    // All Task State
    var taskID by rememberSaveable { mutableStateOf(0) }
    var titleState by rememberSaveable { mutableStateOf("") }
    var descriptionState by rememberSaveable { mutableStateOf("") }
    var categoryState by remember { mutableStateOf("") }
    var emojiState by remember { mutableStateOf("") }
    var urgencyState by remember { mutableStateOf(0) }
    var importanceState by remember { mutableStateOf(0) }
    var dueState by remember { mutableStateOf("") }
    var priorityState by remember { mutableStateOf(Priority.IMPORTANT) }
    var isCompletedState by remember { mutableStateOf(false) }
    var createdAtState by remember { mutableStateOf(0L) }
    val updatedAtState by remember { mutableStateOf(System.currentTimeMillis()) }

    LaunchedEffect(key1 = Unit) {
        // log event to firebase
        val editTaskComposable = bundleOf(
            FirebaseAnalytics.Param.SCREEN_NAME to "Edit Task Screen",
            FirebaseAnalytics.Param.SCREEN_CLASS to "EditTaskScreen.kt"
        )

        viewModel.firebaseLogEvent("edit_screen", editTaskComposable)
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.text_editTask),
                        style = AppTheme.typography.h1,
                        textAlign = TextAlign.Start,
                        color = AppTheme.colors.text,
                        modifier = Modifier.padding(start = 16.dp)
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
                        actions.gotoAddTask.invoke(0, 0).run {
                            // log event to firebase
                            val emptyStateCTAButton = bundleOf(
                                "empty_state_add_task" to "Clicked empty state Add Task button from Edit Task"
                            )

                            viewModel.firebaseLogEvent(
                                "edit_task_empty_state_add_task_button",
                                emptyStateCTAButton
                            )
                        }
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
                        actions.gotoAddTask.invoke(0, 0).run {
                            // log event to firebase
                            val errorBundle = bundleOf(
                                "task_error" to "${taskResult.exception}"
                            )

                            viewModel.firebaseLogEvent("edit_task_error", errorBundle)
                        }
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
                emojiState = currentEmoji.ifEmpty {
                    taskResult.task.emoji
                }

                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(bottom = AppTheme.dimensions.paddingExtraLarge),
                    modifier = modifier
                        .fillMaxSize()
                        .background(
                            AppTheme.colors.background
                        )
                ) {

                    // Emoji
                    item {
                        Spacer(modifier = modifier.height(AppTheme.dimensions.paddingExtraLarge))
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            EmojiPlaceHolder(
                                emoji = emojiState,
                                onSelect = {
                                    scope.launch {
                                        actions.gotoAllEmoji.invoke().run {
                                            // log event to firebase
                                            val emojiBundle = bundleOf(
                                                "all_emoji_bottom_sheet" to "Clicked All Emoji placeholder to open Emoji BottomSheet"
                                            )

                                            viewModel.firebaseLogEvent(
                                                "emoji_bottom_sheet",
                                                emojiBundle
                                            )
                                        }
                                    }
                                }
                            )
                        }
                    }

                    // Title
                    item {
                        Spacer(modifier = modifier.height(AppTheme.dimensions.paddingExtraLarge))
                        EinsenInputTextField(
                            title = stringResource(R.string.text_title),
                            value = titleState
                        ) {
                            titleState = it
                        }
                    }

                    // Description
                    item {
                        Spacer(modifier = modifier.height(AppTheme.dimensions.paddingExtraLarge))
                        EinsenInputTextField(
                            title = stringResource(R.string.text_description),
                            value = descriptionState
                        ) {
                            descriptionState = it
                        }
                    }

                    // Category
                    item {
                        Spacer(modifier = modifier.height(AppTheme.dimensions.paddingExtraLarge))
                        EinsenInputTextField(
                            title = stringResource(R.string.text_category),
                            value = categoryState
                        ) {
                            categoryState = it
                        }
                    }

                    // Due Date Time
                    item {
                        Spacer(modifier = modifier.height(AppTheme.dimensions.paddingExtraLarge))
                        EinsenInputTextField(
                            modifier = Modifier.clickable {
                                val calendar = getCalendar(dueState)
                                context.showDatePicker(calendar) {
                                    calendar.set(
                                        Calendar.DAY_OF_MONTH,
                                        it.get(Calendar.DAY_OF_MONTH)
                                    )
                                    calendar.set(Calendar.MONTH, it.get(Calendar.MONTH))
                                    calendar.set(Calendar.YEAR, it.get(Calendar.YEAR))
                                    context.showTimePicker(calendar) { timeCalendar ->
                                        dueState = formatCalendar(timeCalendar)
                                    }
                                }
                            },
                            title = stringResource(R.string.text_due_date_time),
                            value = dueState,
                            readOnly = true, enabled = false, {}
                        )
                    }

                    // Urgency
                    item {
                        Spacer(modifier = modifier.height(AppTheme.dimensions.paddingExtraLarge))
                        Column(
                            modifier = Modifier.padding(
                                start = AppTheme.dimensions.paddingExtraLarge,
                                end = AppTheme.dimensions.paddingExtraLarge
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.text_urgency),
                                style = AppTheme.typography.subtitle,
                                color = AppTheme.colors.text
                            )
                            Spacer(modifier = Modifier.height(AppTheme.dimensions.paddingLarge))

                            EinsenStepSlider(modifier, points, urgencyState.toFloat()) {
                                urgencyState = it
                            }
                        }
                    }

                    // Importance
                    item {
                        Spacer(modifier = modifier.height(AppTheme.dimensions.paddingExtraLarge))
                        Column(
                            modifier = Modifier.padding(
                                start = AppTheme.dimensions.paddingExtraLarge,
                                end = AppTheme.dimensions.paddingExtraLarge
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.text_importance),
                                style = AppTheme.typography.subtitle,
                                color = AppTheme.colors.text
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
                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar("Please fill all the fields & save the task")
                                    }
                                }
                                else -> {
                                    viewModel.insertTask(task).run {

                                        context.scheduleReminders(task)
                                        showToast(context, "Task updated successfully!")
                                        // log event to firebase
                                        val updateTaskBundle = bundleOf(
                                            "update_task_button" to "Clicked Update Task button to update the task"
                                        )

                                        viewModel.firebaseLogEvent(
                                            "update_task_save_button",
                                            updateTaskBundle
                                        )
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
        modifier = Modifier.padding(
            start = 16.dp,
            top = 16.dp,
            bottom = AppTheme.dimensions.paddingExtraLarge
        ),
        text = stringResource(R.string.tetxt_choose_emoji),
        style = AppTheme.typography.h1,
        textAlign = TextAlign.Start,
        color = AppTheme.colors.text
    )
}
