package dev.spikeysanju.einsen.view.add

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.components.InputTextField
import dev.spikeysanju.einsen.components.PrimaryButton
import dev.spikeysanju.einsen.components.SliderWithTitle
import dev.spikeysanju.einsen.components.TopBarWithBack
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel

@Composable
fun AddTaskScreen(viewModel: MainViewModel, actions: MainActions) {
    Scaffold(topBar = {
        TopBarWithBack(title = "Add Task", actions.upPress)
    }) {

        LazyColumn {

            item {
                Spacer(modifier = Modifier.height(16.dp))
                InputTextField("Title")
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                InputTextField("Description")
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                InputTextField(title = "Category")
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                SliderWithTitle("Urgency")
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                SliderWithTitle("Importance")
            }


            item {
                Spacer(modifier = Modifier.height(24.dp))
                PrimaryButton(title = "Save Task", onclick = {})
            }
        }
    }
}