package dev.spikeysanju.einsen.view.edit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.spikeysanju.einsen.R
import dev.spikeysanju.einsen.components.EmojiPlaceHolder
import dev.spikeysanju.einsen.components.EmojiPlaceHolderSmall
import dev.spikeysanju.einsen.components.InputTextField
import dev.spikeysanju.einsen.components.Message
import dev.spikeysanju.einsen.components.PrimaryButton
import dev.spikeysanju.einsen.components.StepSlider
import dev.spikeysanju.einsen.components.TopBarWithBack
import dev.spikeysanju.einsen.model.Priority
import dev.spikeysanju.einsen.model.Task
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.ui.theme.Avenir
import dev.spikeysanju.einsen.ui.theme.typography
import dev.spikeysanju.einsen.utils.EmojiViewState
import dev.spikeysanju.einsen.utils.SingleViewState
import dev.spikeysanju.einsen.utils.makeValueRound
import dev.spikeysanju.einsen.utils.showToast
import dev.spikeysanju.einsen.view.add.calculatePriority
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun EditTaskScreen(viewModel: MainViewModel, actions: MainActions) {

    // Couroutines scope
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // List and bottom sheet state
    val listState = rememberLazyListState()
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    // All Task State
    var taskID by remember { mutableStateOf(0) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var emojiState by remember { mutableStateOf("") }
    var urgency by remember { mutableStateOf(0F) }
    var importance by remember { mutableStateOf(0F) }
    var due by remember { mutableStateOf("") }
    var priority by remember {
        mutableStateOf(Priority.IMPORTANT)
    }
    var isCompleted by remember { mutableStateOf(false) }
    var createdAt by remember { mutableStateOf(0L) }
    var updatedAt by remember { mutableStateOf(0L) }

    // Slider Step count
    val stepCount by remember { mutableStateOf(5) }

    // Emoji List state
    val result = viewModel.emoji.collectAsState().value

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            BottomSheetTitle()
            LazyVerticalGrid(
                cells = GridCells.Adaptive(minSize = 60.dp)
            ) {

                // get all emoji
                viewModel.getAllEmoji(context = context)

                // parse emoji into ViewStates
                when (result) {
                    EmojiViewState.Empty -> {
                        item {
                            Message(title = "Empty")
                        }
                    }
                    is EmojiViewState.Error -> {
                        item {
                            Message("Error ${result.exception}")
                        }
                    }
                    EmojiViewState.Loading -> {
                        item {
                            Message("Loading")
                        }
                    }
                    is EmojiViewState.Success -> {
                        items(result.emojiItem) { emoji ->
                            EmojiPlaceHolderSmall(
                                emoji = emoji.emoji,
                                onSelect = {
                                    scope.launch {
                                        emojiState = it
                                        bottomSheetState.hide()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopBarWithBack(title = stringResource(R.string.text_editTask), actions.upPress)
            }
        ) {

            when (val taskResult = viewModel.singleTask.collectAsState().value) {
                SingleViewState.Empty -> TODO()
                is SingleViewState.Error -> TODO()
                SingleViewState.Loading -> TODO()
                is SingleViewState.Success -> {

                    // update the task state with latest value
                    taskID = taskResult.task.id
                    title = taskResult.task.title
                    description = taskResult.task.description
                    category = taskResult.task.category
                    emojiState = taskResult.task.emoji
                    urgency = taskResult.task.urgency
                    importance = taskResult.task.importance
                    priority = taskResult.task.priority
                    due = taskResult.task.due
                    isCompleted = taskResult.task.isCompleted
                    createdAt = taskResult.task.createdAt
                    updatedAt = taskResult.task.updatedAt

                    LazyColumn(state = listState, contentPadding = PaddingValues(bottom = 24.dp)) {

                        // Emoji slot
                        item {
                            Spacer(modifier = Modifier.height(24.dp))
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                EmojiPlaceHolder(
                                    emoji = emojiState,
                                    onTap = {
                                        scope.launch {
                                            bottomSheetState.show()
                                        }
                                    }
                                )
                            }
                        }

                        // Title
                        item {
                            Spacer(modifier = Modifier.height(24.dp))
                            InputTextField(
                                title = stringResource(R.string.text_title),
                                value = title
                            ) {
                                title = it
                            }
                        }

                        // Description
                        item {
                            Spacer(modifier = Modifier.height(24.dp))
                            InputTextField(
                                title = stringResource(R.string.text_description),
                                value = description
                            ) {
                                description = it
                            }
                        }

                        // Category
                        item {
                            Spacer(modifier = Modifier.height(24.dp))
                            InputTextField(
                                title = stringResource(R.string.text_category),
                                value = category
                            ) {
                                category = it
                            }
                        }

                        val titleStyle = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = Avenir,
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
                                StepSlider(stepCount = stepCount, value = urgency) {
                                    urgency = it
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
                                StepSlider(stepCount = stepCount, value = importance) {
                                    importance = it
                                }
                            }
                        }

                        // Save Task
                        item {

                            val priorityAverage = importance + urgency / 2

                            Spacer(modifier = Modifier.height(36.dp))
                            PrimaryButton(title = stringResource(R.string.text_save_task)) {
                                val task = Task(
                                    title = title,
                                    description = description,
                                    category = category,
                                    emoji = emojiState,
                                    urgency = makeValueRound(urgency),
                                    importance = makeValueRound(importance),
                                    priority = calculatePriority(priorityAverage),
                                    due = due,
                                    isCompleted = isCompleted,
                                    createdAt = createdAt,
                                    updatedAt = System.currentTimeMillis(),
                                    id = taskID
                                )

                                when {
                                    title.isEmpty() -> showToast(context, "Title is Empty!")
                                    description.isEmpty() -> showToast(
                                        context,
                                        "Description is Empty!"
                                    )
                                    category.isEmpty() -> showToast(context, "Category is Empty!")
                                    else -> viewModel.updateTask(task).run {
                                        showToast(context, "Task Added Successfully!")
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
private fun BottomSheetTitle() {
    Text(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 24.dp),
        text = stringResource(R.string.tetxt_choose_emoji),
        style = typography.h5,
        textAlign = TextAlign.Start,
        color = MaterialTheme.colors.onPrimary
    )
}
