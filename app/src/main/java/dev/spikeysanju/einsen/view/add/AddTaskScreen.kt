package dev.spikeysanju.einsen.view.add

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
import dev.spikeysanju.einsen.components.EmojiPlaceHolder
import dev.spikeysanju.einsen.components.PrimaryButton
import dev.spikeysanju.einsen.components.StepSlider
import dev.spikeysanju.einsen.model.task.Priority
import dev.spikeysanju.einsen.model.task.task
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.ui.theme.Sailec
import dev.spikeysanju.einsen.ui.theme.einsenColors
import dev.spikeysanju.einsen.ui.theme.typography
import dev.spikeysanju.einsen.utils.calculatePriority
import dev.spikeysanju.einsen.utils.showToast
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun AddTaskScreen(viewModel: MainViewModel, actions: MainActions) {

    // component state
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // task value state
    var taskState by remember {
        mutableStateOf(
            task {
                title = ""
                description = ""
                category = ""
                emoji = ""
                urgency = 0F
                importance = 0F
                priority = Priority.IMPORTANT
                due = "18/12/1998"
                isCompleted = false
            }
        )
    }

    val stepCount by rememberSaveable { mutableStateOf(5) }

    // get current emoji
    viewModel.currentEmoji.collectAsState().value.apply {
        taskState = taskState.copy(emoji = this)
    }

    // Add Task Screen content
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.text_addTask),
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

        LazyColumn(state = listState, contentPadding = PaddingValues(bottom = 24.dp)) {

            // Emoji
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    EmojiPlaceHolder(
                        emoji = taskState.emoji,
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
                    value = taskState.title
                ) {
                    taskState = taskState.copy(title = it)
                }
            }

            // Description
            item {
                Spacer(modifier = Modifier.height(24.dp))
                EinsenInputTextField(
                    title = stringResource(R.string.text_description),
                    value = taskState.description
                ) {
                    taskState = taskState.copy(description = it)
                }
            }

            // Category
            item {
                Spacer(modifier = Modifier.height(24.dp))
                EinsenInputTextField(
                    title = stringResource(R.string.text_category),
                    value = taskState.category
                ) {
                    taskState = taskState.copy(category = it)
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
                    StepSlider(stepCount = stepCount, value = taskState.urgency) {
                        taskState = taskState.copy(urgency = it)
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
                    StepSlider(stepCount = stepCount, value = taskState.importance) {
                        taskState = taskState.copy(importance = it)
                    }
                }
            }

            // Save Task CTA
            item {
                Spacer(modifier = Modifier.height(36.dp))
                PrimaryButton(title = stringResource(R.string.text_save_task)) {

                    // calculate the average value by adding urgency + priority / 2
                    val priorityAverage = taskState.importance + taskState.urgency / 2
                    taskState = taskState.copy(priority = calculatePriority(priorityAverage))

                    when {
                        taskState.title.isEmpty() && taskState.description.isEmpty() || taskState.category.isEmpty() -> {
                            showToast(context, "Please fill all the fields & save the task")
                        }
                        else -> {
                            viewModel.insertTask(taskState).run {
                                showToast(context, "Task added successfully!")
                                actions.upPress.invoke()
                            }
                        }
                    }
                }
            }
        }
    }
}
