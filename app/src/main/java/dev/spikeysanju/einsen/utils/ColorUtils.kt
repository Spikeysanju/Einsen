package dev.spikeysanju.einsen.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

fun fromColor(color: Color): Int {
    return color.toArgb()
}

fun toColor(color: Int): Color {
    return Color(color)
}


fun makeValueRound(value: Float): Float {
    return when (value) {
        0.0F -> 0.0F
        0.8F -> 1.0F
        1.6F -> 2.0F
        2.4F -> 3.0F
        3.2F -> 4.0F
        4.0F -> 5.0F
        else -> 0.0F
    }
}