package dev.spikeysanju.einsen.components

import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun Slider() {
    var sliderState by remember { mutableStateOf(0F) }
    Slider(
        value = sliderState, onValueChange = {
            sliderState = it
        }, valueRange = 0f..4f, steps = 4, colors = SliderDefaults.colors(
            thumbColor = colors.primary,
            activeTrackColor = colors.primary,
            inactiveTrackColor = colors.primaryVariant,
            disabledThumbColor = colors.secondaryVariant
        )
    )
}