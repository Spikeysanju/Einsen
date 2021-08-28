package dev.spikeysanju.einsen.ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

data class MyColors(
    val material: Colors,
    val success: Color,
    val err: Color,
    val warning: Color,
    val calm: Color,
    val card: Color,
    val bg: Color,
    val text: Color,
    val button: Color,
    val icon: Color,
    val black: Color,
    val white: Color
) {
    val primary: Color get() = material.primary
    val primaryVariant: Color get() = material.primaryVariant
    val secondary: Color get() = material.secondary
    val secondaryVariant: Color get() = material.secondaryVariant
    val background: Color get() = material.background
    val surface: Color get() = material.surface
    val error: Color get() = material.error
    val onPrimary: Color get() = material.onPrimary
    val onSecondary: Color get() = material.onSecondary
    val onBackground: Color get() = material.onBackground
    val onSurface: Color get() = material.onSurface
    val onError: Color get() = material.onError
    val isLight: Boolean get() = material.isLight
}
