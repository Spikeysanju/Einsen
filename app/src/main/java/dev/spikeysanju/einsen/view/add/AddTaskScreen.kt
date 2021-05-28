package dev.spikeysanju.einsen.view.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.components.InputTextField
import dev.spikeysanju.einsen.components.PrimaryButton
import dev.spikeysanju.einsen.components.TopBarWithBack
import dev.spikeysanju.einsen.model.Task
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.ui.theme.typography
import dev.spikeysanju.einsen.utils.toast
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel

@Composable
fun AddTaskScreen(viewModel: MainViewModel, actions: MainActions) {
    Scaffold(topBar = {
        TopBarWithBack(title = "Add Task", actions.upPress)
    }) {

        val context = LocalContext.current
        var title by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        var category by remember { mutableStateOf("") }
        var tags by remember { mutableStateOf("") }
        val listState = rememberLazyListState()
        var urgencyState by remember { mutableStateOf(0F) }
        var importanceState by remember { mutableStateOf(0F) }
        val stepCount by remember { mutableStateOf(5) }

        LazyColumn(state = listState) {

            item {
                Spacer(modifier = Modifier.height(16.dp))
                InputTextField(title = "Title", value = title) {
                    title = it
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                InputTextField(title = "Description", value = description) {
                    description = it
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                InputTextField(title = "Category", value = category) {
                    category = it
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Urgency",
                        style = typography.subtitle1,
                        color = MaterialTheme.colors.onPrimary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Column(
                        Modifier
                            .fillMaxWidth()
                    ) {
                        Slider(
                            value = urgencyState,
                            onValueChange = { urgencyState = it },
                            valueRange = 0f..4f,
                            steps = 4,
                            colors = SliderDefaults.colors(
                                thumbColor = MaterialTheme.colors.primary,
                                activeTrackColor = MaterialTheme.colors.primary,
                                inactiveTrackColor = MaterialTheme.colors.primaryVariant,
                                disabledThumbColor = MaterialTheme.colors.secondaryVariant
                            )
                        )
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            for (i in 0..stepCount) {
                                Text(
                                    i.toString(),
                                    style = typography.subtitle1,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colors.onPrimary
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Importance",
                        style = typography.subtitle1,
                        color = MaterialTheme.colors.onPrimary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Column(
                        Modifier
                            .fillMaxWidth()
                    ) {
                        Slider(
                            value = importanceState,
                            onValueChange = { importanceState = it },
                            valueRange = 0f..4f,
                            steps = 4,
                            colors = SliderDefaults.colors(
                                thumbColor = MaterialTheme.colors.primary,
                                activeTrackColor = MaterialTheme.colors.primary,
                                inactiveTrackColor = MaterialTheme.colors.primaryVariant,
                                disabledThumbColor = MaterialTheme.colors.secondaryVariant
                            )
                        )
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            for (i in 0..stepCount) {
                                Text(
                                    i.toString(),
                                    style = typography.subtitle1,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colors.onPrimary
                                )
                            }
                        }
                    }
                }
            }


            item {
                Spacer(modifier = Modifier.height(24.dp))
                PrimaryButton(title = "Save Task") {
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


