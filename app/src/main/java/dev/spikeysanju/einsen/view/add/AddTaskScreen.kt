package dev.spikeysanju.einsen.view.add

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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
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
import androidx.compose.ui.res.painterResource
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
import dev.spikeysanju.einsen.model.Priority
import dev.spikeysanju.einsen.model.Task
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.ui.theme.Avenir
import dev.spikeysanju.einsen.ui.theme.einsenColors
import dev.spikeysanju.einsen.ui.theme.typography
import dev.spikeysanju.einsen.utils.EmojiViewState
import dev.spikeysanju.einsen.utils.makeValueRound
import dev.spikeysanju.einsen.utils.showToast
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun AddTaskScreen(viewModel: MainViewModel, actions: MainActions) {
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var emojiState by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    var urgencyState by remember { mutableStateOf(0F) }
    var importanceState by remember { mutableStateOf(0F) }
    val stepCount by remember { mutableStateOf(5) }
    val result = viewModel.emoji.collectAsState().value
    var priority by remember {
        mutableStateOf(Priority.IMPORTANT)
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            BottomSheetTitle()
            LazyVerticalGrid(
                cells = GridCells.Adaptive(minSize = 60.dp)
            ) {
                // get all emoji
                viewModel.getAllEmoji(context)
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
                    InputTextField(title = stringResource(R.string.text_title), value = title) {
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
                        StepSlider(stepCount = stepCount, value = urgencyState) {
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
                        StepSlider(stepCount = stepCount, value = importanceState) {
                            importanceState = it
                        }
                    }
                }

                // Save Task
                item {
                    Spacer(modifier = Modifier.height(36.dp))
                    PrimaryButton(title = stringResource(R.string.text_save_task)) {

                        val priorityAverage = urgencyState + importanceState / 2
                        priority = calculatePriority(priorityAverage)

                        val task = Task(
                            title = title,
                            description = description,
                            category = category,
                            emoji = emojiState,
                            urgency = makeValueRound(urgencyState),
                            importance = makeValueRound(importanceState),
                            priority = priority,
                            due = "18/12/2021",
                            isCompleted = false
                        )

                        when {
                            title.isEmpty() -> showToast(context, "Title is Empty!")
                            description.isEmpty() -> showToast(context, "Description is Empty!")
                            category.isEmpty() -> showToast(context, "Category is Empty!")
                            else -> viewModel.insertTask(task).run {
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

fun calculatePriority(priorityAverage: Float): Priority {

    return when {
        priorityAverage >= 4 -> {
            Priority.URGENT
        }
        priorityAverage >= 3 -> {
            Priority.IMPORTANT
        }
        priorityAverage >= 2 -> {
            Priority.DELEGATE
        }
        priorityAverage >= 1 -> {
            Priority.DUMP
        }
        else -> {
            Priority.URGENT
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
