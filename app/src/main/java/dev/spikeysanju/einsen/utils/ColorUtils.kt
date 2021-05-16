package dev.spikeysanju.einsen.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

fun fromColor(color: Color): Int {
    return color.toArgb()
}

fun toColor(color: Int): Color {
    return Color(color)
}