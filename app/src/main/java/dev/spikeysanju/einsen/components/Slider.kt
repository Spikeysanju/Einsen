package dev.spikeysanju.einsen.components

import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.spikeysanju.einsen.ui.theme.blue100
import dev.spikeysanju.einsen.ui.theme.blue300

@Composable
fun Slider() {
    var sliderState by remember { mutableStateOf(0F) }
    Slider(
        value = sliderState, onValueChange = {
            sliderState = it
        }, valueRange = 0f..4f, steps = 4, colors = SliderDefaults.colors(
            thumbColor = blue100,
            activeTrackColor = blue300
        )
    )
}