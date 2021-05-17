package dev.spikeysanju.einsen.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = gray100,
    primaryVariant = gray200,
    secondary = gray300,
    secondaryVariant = gray400,
    onPrimary = white,
    onSecondary = black,
    background = gray500,
    onError = error,
    onSurface = success
)

private val LightColorPalette = lightColors(
    primary = gray100,
    primaryVariant = gray200,
    secondary = gray300,
    secondaryVariant = gray400,
    onPrimary = white,
    onSecondary = black,
    background = gray500,
    onError = error,
    onSurface = success
)

// TODO: ADD MORE THEMES HERE

@Composable
fun EinsenTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}