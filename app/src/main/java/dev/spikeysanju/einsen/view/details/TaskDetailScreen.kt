package dev.spikeysanju.einsen.view.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.R
import dev.spikeysanju.einsen.components.*
import dev.spikeysanju.einsen.model.Task
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.ui.theme.typography
import dev.spikeysanju.einsen.utils.SingleViewState
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel

@Composable
fun TaskDetailsScreen(viewModel: MainViewModel, action: MainActions) {
    val buttonState = remember {
        mutableStateOf(false)
    }
    val taskState = remember {
        mutableStateOf(
            Task(
                "",
                "",
                "",
                "",
                0F,
                0F,
                "",
                false,
                0,
                0
            )
        )
    }

    Scaffold(topBar = {
        TopBarWithBack(title = stringResource(id = R.string.text_taskDetails), action.upPress)
    }, bottomBar = {
        BottomCTA(onEdit = {
            // Todo edit notes
        }, onDelete = {
            viewModel.deleteTaskByID(taskState.value.id)
        }, onShare = {
            //Todo share notes
        }, onButtonChange = {
            viewModel.updateStatus(taskState.value.id, !taskState.value.isCompleted)
            buttonState.value = !taskState.value.isCompleted
        }, buttonState = buttonState.value)

    }) {

        val listState = rememberLazyListState()

        when (val result = viewModel.singleTask.collectAsState().value) {

            is SingleViewState.Success -> {
                LazyColumn(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    state = listState
                ) {

                    // update the task state with lates value
                    val task = result.task
                    taskState.value = result.task


                    // Emoji placeholder
                    item {
                        Spacer(modifier = Modifier.height(24.dp))

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            EmojiPlaceHolder(emoji = task.emoji, onTap = {

                            })
                        }
                    }

                    // Category + Title + Description
                    item {

                        Spacer(modifier = Modifier.height(16.dp))
                        ChipView(title = task.category)

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