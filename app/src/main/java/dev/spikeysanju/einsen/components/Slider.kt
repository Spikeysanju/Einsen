package dev.spikeysanju.einsen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.ui.theme.typography


@Composable
fun Slider() {
    var sliderState by remember { mutableStateOf(0F) }
    val maxValue by remember { mutableStateOf(5) }

    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Slider(
            value = sliderState,
            onValueChange = { sliderState = it },
            valueRange = 0f..4f,
            steps = 4,
            colors = SliderDefaults.colors(
                thumbColor = colors.primary,
                activeTrackColor = colors.primary,
                inactiveTrackColor = colors.primaryVariant,
                disabledThumbColor = colors.secondaryVariant
            )
        )
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in 0..maxValue) {
                Text(
                    i.toString(),
                    style = typography.subtitle1,
                    textAlign = TextAlign.Center,
                    color = colors.onPrimary
                )
            }
        }
    }
}


@Composable
fun SliderWithTitle(title: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = title, style = typography.subtitle1, color = colors.onPrimary)
        Spacer(modifier = Modifier.height(12.dp))
        Slider()
    }
}