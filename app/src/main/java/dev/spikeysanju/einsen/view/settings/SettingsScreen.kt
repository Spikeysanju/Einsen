package dev.spikeysanju.einsen.view.settings

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.R
import dev.spikeysanju.einsen.components.CraneEditableUserInput
import dev.spikeysanju.einsen.components.CraneUserInput
import dev.spikeysanju.einsen.components.SimpleUserInput
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.ui.theme.einsenColors
import dev.spikeysanju.einsen.ui.theme.typography
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel

@Composable
fun SettingsScreen(viewModel: MainViewModel, actions: MainActions) {
    val listState = rememberLazyListState()
    var text by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.text_settings),
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
        LazyColumn(state = listState) {
            item {
                CraneUserInput(
                    text,
                    onClick = {},
                    caption = "Title",
                    vectorImageId = R.drawable.ic_settings,
                    tint = einsenColors.black
                )
                Spacer(modifier = Modifier.height(12.dp))
                SimpleUserInput(text, caption = "Title", vectorImageId = R.drawable.ic_settings)
                Spacer(modifier = Modifier.height(12.dp))
                CraneEditableUserInput(hint = "text", "Title", R.drawable.ic_add, onInputChanged = {
                    text = it
                })
            }

        }
    }
}
