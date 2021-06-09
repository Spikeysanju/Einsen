package dev.spikeysanju.einsen.ui.theme

import android.annotation.SuppressLint
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
    onPrimary = white, // text
    onSecondary = black, // bg
    background = black, // bg
    onError = error,
    onSurface = success
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = white100,
    primaryVariant = white200,
    secondary = white300,
    secondaryVariant = white400,
    onPrimary = black, // text
    onSecondary = white, // bg
    background = white400, // bg
    onError = error,
    onSurface = success
)

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