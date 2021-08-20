package dev.spikeysanju.einsen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.spikeysanju.einsen.R

@Composable
fun TabBar(onTabSelect: (String) -> Unit) {
    val tabSelectionState = remember {
        mutableStateOf("")
    }

    val todo = stringResource(R.string.text_todo)
    val doing = stringResource(R.string.text_doing)
    val done = stringResource(R.string.text_done)

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        ChipView(title = todo, onClick = {
            tabSelectionState.value = todo
        })

        ChipView(title = doing, onClick = {
            tabSelectionState.value = doing
        })

        ChipView(title = done, onClick = {
            tabSelectionState.value = done
        })
    }

}

