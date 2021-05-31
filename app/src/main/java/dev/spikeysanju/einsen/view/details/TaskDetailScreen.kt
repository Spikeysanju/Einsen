package dev.spikeysanju.einsen.view.details

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.R
import dev.spikeysanju.einsen.components.InfoCard
import dev.spikeysanju.einsen.components.TopBarWithBack
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.ui.theme.typography
import dev.spikeysanju.einsen.utils.SingleViewState
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

@Composable
fun TaskDetailsScreen(viewModel: MainViewModel, action: MainActions) {
    Scaffold(topBar = {
        TopBarWithBack(title = stringResource(id = R.string.text_taskDetails), action.upPress)
    }) {

        val listState = rememberLazyListState()

        when (val result = viewModel.singleTask.collectAsState().value) {

            is SingleViewState.Success -> {
                LazyColumn(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    state = listState
                ) {

                    val task = result.task
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = task.category,
                            style = typography.subtitle1,
                            textAlign = TextAlign.Start,
                            color = colors.onPrimary
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
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            InfoCard(
                                title = stringResource(R.string.text_urgency),
                                value = task.urgency.toString()
                            )

                            InfoCard(
                                title = stringResource(R.string.text_importance),
                                value = task.importance.toString()
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


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LoadSingleTask(viewModel: MainViewModel, taskID: Long) {
    val scope = rememberCoroutineScope()
    scope.launch(IO) {
        viewModel.findTaskByID(taskID)
    }
}