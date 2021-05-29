package dev.spikeysanju.einsen.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val BlueColorPalette = lightColors(
    primary = blue100,
    primaryVariant = blue200,
    secondary = blue300,
    secondaryVariant = blue400,
    onPrimary = white,
    onSecondary = black,
    background = blue500,
    onError = error,
    onSurface = success
)

private val PurpleColorPalette = lightColors(
    primary = purple100,
    primaryVariant = purple200,
    secondary = purple300,
    secondaryVariant = purple400,
    onPrimary = white,
    onSecondary = black,
    background = purple500,
    onError = error,
    onSurface = success
)

private val OliverColorPalette = lightColors(
    primary = oliver100,
    primaryVariant = oliver200,
    secondary = oliver300,
    secondaryVariant = oliver400,
    onPrimary = white,
    onSecondary = black,
    background = oliver500,
    onError = error,
    onSurface = success
)


private val RedColorPalette = lightColors(
    primary = red100,
    primaryVariant = red200,
    secondary = red300,
    secondaryVariant = red400,
    onPrimary = white,
    onSecondary = black,
    background = red500,
    onError = error,
    onSurface = success
)


private val GreenColorPalette = darkColors(
    primary = green100,
    primaryVariant = green200,
    secondary = green300,
    secondaryVariant = green400,
    onPrimary = white,
    onSecondary = black,
    background = green500,
    onError = error,
    onSurface = success
)

private val GrayColorPalette = lightColors(
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

@Composable
fun EinsenTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {

    val colors = if (darkTheme) {
        GrayColorPalette
    } else {
        GrayColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}