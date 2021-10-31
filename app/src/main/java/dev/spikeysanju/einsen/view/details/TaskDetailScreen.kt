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

package dev.spikeysanju.einsen.view.details

import android.app.Activity
import android.content.Intent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ShareCompat
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import dev.spikeysanju.einsen.R
import dev.spikeysanju.einsen.components.BottomCTA
import dev.spikeysanju.einsen.components.ChipView
import dev.spikeysanju.einsen.components.EmojiPlaceHolder
import dev.spikeysanju.einsen.components.InfoCard
import dev.spikeysanju.einsen.model.task.Priority
import dev.spikeysanju.einsen.model.task.task
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.ui.theme.einsenColors
import dev.spikeysanju.einsen.ui.theme.typography
import dev.spikeysanju.einsen.utils.viewstate.SingleViewState
import dev.spikeysanju.einsen.view.animationviewstate.AnimationViewState
import dev.spikeysanju.einsen.view.animationviewstate.ScreenState
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel
import java.util.*

@Composable
fun TaskDetailsScreen(
    modifier: Modifier,
    viewModel: MainViewModel,
    action: MainActions,
    mFirebaseAnalytics: FirebaseAnalytics
) {

    LaunchedEffect(key1 = Unit) {
        // log event to firebase
        val taskDetailsComposableBundle = bundleOf(
            FirebaseAnalytics.Param.SCREEN_NAME to "Task Details Screen",
            FirebaseAnalytics.Param.SCREEN_CLASS to "TaskDetailsScreen.kt"
        )

        mFirebaseAnalytics.logEvent(
            "task_details_screen",
            taskDetailsComposableBundle
        )
    }

    val activity = LocalContext.current as Activity

    var taskState by remember {
        mutableStateOf(
            task {
                title = ""
                description = ""
                category = ""
                emoji = ""
                urgency = 0
                importance = 0
                priority = Priority.IMPORTANT
                due = "18/12/1998"
                isCompleted = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.text_taskDetails),
                        style = typography.h6,
                        textAlign = TextAlign.Start,
                        color = einsenColors.black,
                        modifier = modifier.padding(start = 16.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { action.upPress.invoke() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = stringResource(R.string.back_button),
                            tint = einsenColors.black
                        )
                    }
                },
                backgroundColor = einsenColors.background, elevation = 0.dp
            )
        },
        bottomBar = {

            val buttonColor by animateColorAsState(if (taskState.isCompleted) einsenColors.err else einsenColors.success)

            val buttonTitle = when (taskState.isCompleted) {
                true -> stringResource(id = R.string.text_incomplete)
                false -> stringResource(id = R.string.text_complete)
            }

            val buttonIcon = when (taskState.isCompleted) {
                true -> painterResource(id = R.drawable.ic_incomplete)
                false -> painterResource(id = R.drawable.ic_check)
            }

            BottomCTA(
                onEdit = {
                    action.gotoEditTask(taskState.id).run {
                        // log event to firebase
                        val editTaskBundle = bundleOf(
                            "edit_task_button" to "Clicked Edit Task button to edit the task"
                        )

                        mFirebaseAnalytics.logEvent(
                            "task_details_edit_task_button",
                            editTaskBundle
                        )
                    }
                },
                onDelete = {
                    viewModel.deleteTaskByID(taskState.id).run {
                        action.upPress.invoke().run {
                            // log event to firebase
                            val deleteTaskBundle = bundleOf(
                                "delete_task_button" to "Clicked Delete Task button to delete the task"
                            )

                            mFirebaseAnalytics.logEvent(
                                "task_details_delete_task_button",
                                deleteTaskBundle
                            )
                        }
                    }
                },
                onShare = {
                    val createdAt = Date(taskState.createdAt) // Date
                    shareNote(
                        activity = activity,
                        taskState.emoji,
                        taskState.title,
                        taskState.description,
                        taskState.category,
                        taskState.priority.name,
                        createdAt,
                    ).run {

                        // log event to firebase
                        val editTaskBundle = bundleOf(
                            "share_task_button" to "Clicked Share Task button to share the task"
                        )

                        mFirebaseAnalytics.logEvent(
                            "task_details_share_task_button",
                            editTaskBundle
                        )
                    }
                },
                onButtonChange = {
                    viewModel.updateStatus(taskState.id, !taskState.isCompleted).run {
                        // log event to firebase
                        val updateStatusBundle = bundleOf(
                            "update_task_button" to "Clicked Update Task button to update the status of the task"
                        )

                        mFirebaseAnalytics.logEvent(
                            "task_details_share_task_button",
                            updateStatusBundle
                        )
                    }
                },
                title = buttonTitle, icon = buttonIcon, color = buttonColor
            )
        }
    ) {
        val listState = rememberLazyListState()

        when (val result = viewModel.singleTask.collectAsState().value) {

            is SingleViewState.Success -> {

                LazyColumn(
                    modifier = modifier.padding(start = 16.dp, end = 16.dp),
                    state = listState,
                    contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp)
                ) {

                    // update the task state with latest value
                    val task = result.task
                    taskState = task

                    item {
                        Box(
                            modifier = modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .clip(RoundedCornerShape(12.dp))
                                .background(einsenColors.card),
                            contentAlignment = Alignment.TopCenter
                        ) {

                            Column(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        bottom = 24.dp,
                                        top = 24.dp
                                    )
                            ) {

                                Spacer(modifier = modifier.height(24.dp))

                                // Emoji view
                                Box(
                                    modifier = modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    EmojiPlaceHolder(
                                        emoji = task.emoji,
                                        onSelect = {
                                        }
                                    )
                                }

                                Spacer(modifier = modifier.height(16.dp))

                                // Category chip
                                ChipView(
                                    title = task.category,
                                    onClick = {
                                        // Do nothing...
                                    }
                                )

                                Spacer(modifier = modifier.height(16.dp))

                                // Title
                                Text(
                                    text = task.title,
                                    style = typography.h5,
                                    textAlign = TextAlign.Start,
                                    color = colors.onPrimary
                                )

                                Spacer(modifier = modifier.height(24.dp))

                                // Description
                                Text(
                                    text = task.description,
                                    style = typography.body1,
                                    textAlign = TextAlign.Start,
                                    color = colors.onPrimary
                                )

                                Spacer(modifier = modifier.height(24.dp))

                                // Priority score card
                                Row(
                                    modifier = modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    val weight = modifier.weight(1f)
                                    InfoCard(
                                        title = stringResource(R.string.text_urgency),
                                        value = task.urgency.toString(),
                                        modifier = weight
                                    )

                                    Spacer(modifier = modifier.width(12.dp))

                                    InfoCard(
                                        title = stringResource(R.string.text_importance),
                                        value = task.importance.toString(),
                                        modifier = weight
                                    )
                                }
                            }
                        }
                    }
                }
            }
            SingleViewState.Empty -> {
                AnimationViewState(
                    modifier,
                    title = stringResource(R.string.text_no_task_title),
                    description = stringResource(R.string.text_no_task_description),
                    callToAction = stringResource(R.string.text_add_a_task),
                    ScreenState.EMPTY,
                    actions = {
                        action.gotoAddTask.invoke(0, 0).run {
                            // log event to firebase
                            val emptyStateCTAButton = bundleOf(
                                "empty_state_add_task" to "Clicked empty state Add Task button from Task Details"
                            )

                            mFirebaseAnalytics.logEvent(
                                "task_details_empty_state_add_task_button",
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
                        action.gotoAddTask.invoke(0, 0).run {
                            // log event to firebase
                            val errorBundle = bundleOf(
                                "task_details_error" to "${result.exception}"
                            )

                            mFirebaseAnalytics.logEvent(
                                "task_details_error",
                                errorBundle
                            )
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
                        action.gotoAddTask.invoke(0, 0)
                    }
                )
            }
        }
    }
}

fun shareNote(
    activity: Activity,
    emoji: String,
    title: String,
    description: String,
    category: String,
    priority: String,
    createdAt: Date
) {
    val shareMsg = activity.getString(
        R.string.text_message_share,
        emoji,
        title,
        description,
        category,
        priority,
        createdAt
    )

    val intent = ShareCompat.IntentBuilder(activity)
        .setType("text/plain")
        .setText(shareMsg)
        .intent

    activity.startActivity(Intent.createChooser(intent, null))
}
