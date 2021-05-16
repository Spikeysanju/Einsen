package dev.spikeysanju.einsen.model

import androidx.compose.ui.graphics.Color
import dev.spikeysanju.einsen.ui.theme.blue100
import dev.spikeysanju.einsen.ui.theme.gray100
import dev.spikeysanju.einsen.ui.theme.green100

enum class ThemeType(val color: Color) {
    BLUE(blue100),
    GREEN(green100),
    GRAY(gray100)
}