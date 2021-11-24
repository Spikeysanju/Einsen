/*
 *
 *  * Copyright 2021 Spikey Sanju
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     https://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

package dev.spikeysanju.einsen.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.ui.theme.einsenColors

/**
 * This customer slider component helps to show the slider with step indicator.
 * @param points
 * @param value
 * @param onValueChange
 *
 * Code reference - https://github.com/PiotrPrus/ComposeWeatherCard
 */

@Composable
fun EinsenStepSlider(
    modifier: Modifier = Modifier,
    points: List<String>,
    value: Float,
    onValueChange: (Int) -> Unit
) {
    val (sliderValue, setSliderValue) = remember { mutableStateOf(value) }
    val drawPadding = with(LocalDensity.current) { 10.dp.toPx() }
    val textSize = with(LocalDensity.current) { 10.dp.toPx() }
    val lineHeightDp = 10.dp
    val lineHeightPx = with(LocalDensity.current) { lineHeightDp.toPx() }
    val canvasHeight = 50.dp
    val textPaint = android.graphics.Paint().apply {
        color = einsenColors.black.toArgb()
        textAlign = android.graphics.Paint.Align.CENTER
        this.textSize = textSize
    }
    Box(contentAlignment = Alignment.Center) {
        Canvas(
            modifier = modifier
                .height(canvasHeight)
                .fillMaxWidth()
                .padding(
                    top = canvasHeight
                        .div(2)
                        .minus(lineHeightDp.div(2))
                )
        ) {
            val yStart = 0f
            val distance = (size.width.minus(2 * drawPadding)).div(points.size.minus(1))
            points.forEachIndexed { index, step ->
                drawLine(
                    color = Color.DarkGray,
                    start = Offset(x = drawPadding + index.times(distance), y = yStart),
                    end = Offset(x = drawPadding + index.times(distance), y = lineHeightPx)
                )
                if (index.rem(1) == 0) {
                    this.drawContext.canvas.nativeCanvas.drawText(
                        step,
                        drawPadding + index.times(distance),
                        size.height,
                        textPaint
                    )
                }
            }
        }
        Slider(
            modifier = modifier.fillMaxWidth(),
            value = sliderValue,
            valueRange = 0f..points.size.minus(1).toFloat(),
            steps = points.size.minus(2),
            colors = SliderDefaults.colors(
                thumbColor = einsenColors.card,
                activeTrackColor = einsenColors.card,
                inactiveTrackColor = colors.primaryVariant,
                disabledThumbColor = colors.secondaryVariant
            ),
            onValueChange = {
                setSliderValue(it)
                onValueChange(it.toInt())
            }
        )
    }
}

@Preview(name = "Important & Urgency Slider", group = "Slider")
@Composable
fun SliderPreview() {
    Column {
        Slider(
            value = 4F,
            onValueChange = {
                // slider value change goes here
            }
        )
    }
}
