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

package dev.spikeysanju.einsen.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

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

private val LightColors = EinsenColors(
    material = LightColorPalette,
    warning = warning,
    success = success,
    err = error,
    calm = calm,
    card = card,
    bg = bg,
    text = black,
    button = black,
    icon = black,
    black = black,
    white = white
)

private val DarkColors = EinsenColors(
    material = DarkColorPalette,
    warning = warning,
    success = success,
    err = error,
    calm = calm,
    card = cardDark,
    bg = bgDark,
    text = black,
    button = white,
    icon = white,
    black = white,
    white = black
)

private val LocalColors = staticCompositionLocalOf { LightColors }

val einsenColors: EinsenColors
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current

@Composable
fun EinsenTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {

    val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    CompositionLocalProvider(LocalColors provides colors) {
        MaterialTheme(
            colors = colors.material,
            content = content,
            shapes = Shapes,
            typography = typography
        )
    }
}
