package dev.spikeysanju.einsen.view.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.R
import dev.spikeysanju.einsen.components.InputTextField
import dev.spikeysanju.einsen.components.PrimaryButton
import dev.spikeysanju.einsen.components.StepSlider
import dev.spikeysanju.einsen.components.TopBarWithBack
import dev.spikeysanju.einsen.model.Task
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.ui.theme.typography
import dev.spikeysanju.einsen.utils.toast
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel

@Composable
fun AddTaskScreen(viewModel: MainViewModel, actions: MainActions) {
    Scaffold(topBar = {
        TopBarWithBack(title = stringResource(R.string.text_addTask), actions.upPress)
    }) {

        val context = LocalContext.current
        var title by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        var category by remember { mutableStateOf("") }
        val listState = rememberLazyListState()
        var urgencyState by remember { mutableStateOf(0F) }
        var importanceState by remember { mutableStateOf(0F) }
        val stepCount by remember { mutableStateOf(5) }

        LazyColumn(state = listState) {

            // Title
            item {
                Spacer(modifier = Modifier.height(16.dp))
                InputTextField(title = stringResource(R.string.text_title), value = title) {
                    title = it
                }
            }

            // Description
            item {
                Spacer(modifier = Modifier.height(16.dp))
                InputTextField(
                    title = stringResource(R.string.text_description),
                    value = description
                ) {
                    description = it
                }
            }

            // Category
            item {
                Spacer(modifier = Modifier.height(16.dp))
                InputTextField(title = stringResource(R.string.text_category), value = category) {
                    category = it
                }
            }

            // Urgency
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(R.string.text_urgency),
                        style = typography.subtitle1,
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
                Spacer(modifier = Modifier.height(16.dp))
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(R.string.text_importance),
                        style = typography.subtitle1,
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
                Spacer(modifier = Modifier.height(24.dp))
                PrimaryButton(title = stringResource(R.string.text_save_task)) {
                    val task = Task(
                        title = title,
                        description = description,
                        category = category,
                        urgency = urgencyState,
                        importance = importanceState,
                        timer = 25F,
                        isCompleted = false
                    )
                    viewModel.insertTask(task).run {
                        context.toast("Task Added Successfully!")
                    }
                }
            }
        }

    }
}


