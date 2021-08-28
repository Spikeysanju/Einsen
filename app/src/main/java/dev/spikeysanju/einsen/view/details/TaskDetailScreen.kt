package dev.spikeysanju.einsen.view.details

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.R
import dev.spikeysanju.einsen.components.BottomCTA
import dev.spikeysanju.einsen.components.ChipView
import dev.spikeysanju.einsen.components.EmojiPlaceHolder
import dev.spikeysanju.einsen.components.InfoCard
import dev.spikeysanju.einsen.model.Priority
import dev.spikeysanju.einsen.model.Task
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.ui.theme.myColors
import dev.spikeysanju.einsen.ui.theme.typography
import dev.spikeysanju.einsen.utils.SingleViewState
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel

@Composable
fun TaskDetailsScreen(viewModel: MainViewModel, action: MainActions) {

    var taskState by remember {
        mutableStateOf(
            Task(
                "",
                "",
                "",
                "",
                0F,
                0F,
                Priority.IMPORTANT,
                "0",
                false,
                0,
                0
            )
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
                        color = myColors.black,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { action.upPress.invoke() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = stringResource(R.string.back_button),
                            tint = myColors.black
                        )
                    }
                },
                backgroundColor = myColors.background, elevation = 0.dp
            )
        },
        bottomBar = {

            val buttonColor by animateColorAsState(if (taskState.isCompleted) myColors.err else myColors.success)

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
                    action.gotoEditTask(taskState.id)
                },
                onDelete = {
                    viewModel.deleteTaskByID(taskState.id).run {
                        action.upPress.invoke()
                    }
                },
                onShare = {
                    // Todo share notes
                },
                onButtonChange = {
                    viewModel.updateStatus(taskState.id, !taskState.isCompleted)
                },
                title = buttonTitle, icon = buttonIcon, color = buttonColor
            )
        }
    ) {

        val listState = rememberLazyListState()

        when (val result = viewModel.singleTask.collectAsState().value) {

            is SingleViewState.Success -> {
                LazyColumn(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    state = listState,
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {

                    // update the task state with latest value
                    val task = result.task
                    taskState = result.task

                    // Emoji placeholder
                    item {
                        Spacer(modifier = Modifier.height(24.dp))

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            EmojiPlaceHolder(
                                emoji = task.emoji,
                                onTap = {
                                }
                            )
                        }
                    }

                    // Category + Title + Description
                    item {

                        Spacer(modifier = Modifier.height(16.dp))
                        ChipView(
                            title = task.category,
                            onClick = {
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = task.title,
                            style = typography.h5,
                            textAlign = TextAlign.Start,
                            color = colors.onPrimary
                        )

                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = task.description,
                            style = typography.body1,
                            textAlign = TextAlign.Start,
                            color = colors.onPrimary
                        )
                    }

                    // Info card
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            val weight = Modifier.weight(1f)
                            InfoCard(
                                title = stringResource(R.string.text_urgency),
                                value = task.urgency.toString(),
                                modifier = weight
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            InfoCard(
                                title = stringResource(R.string.text_importance),
                                value = task.importance.toString(),
                                modifier = weight
                            )
                        }
                    }
                }
            }
            SingleViewState.Empty -> {
                Text(text = "Empty")
            }
            is SingleViewState.Error -> {
                Text(text = "Error ${result.exception}")
            }
            SingleViewState.Loading -> {
            }
        }
    }
}
