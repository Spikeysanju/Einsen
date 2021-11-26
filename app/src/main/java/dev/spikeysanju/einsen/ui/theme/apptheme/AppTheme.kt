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

package dev.spikeysanju.einsen.ui.theme.apptheme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import dev.spikeysanju.einsen.ui.theme.color.AppColors
import dev.spikeysanju.einsen.ui.theme.color.LocalColors
import dev.spikeysanju.einsen.ui.theme.dimensions.AppDimensions
import dev.spikeysanju.einsen.ui.theme.dimensions.LocalDimensions
import dev.spikeysanju.einsen.ui.theme.shape.AppShapes
import dev.spikeysanju.einsen.ui.theme.shape.LocalShapes
import dev.spikeysanju.einsen.ui.theme.typography.AppTypography
import dev.spikeysanju.einsen.ui.theme.typography.LocalTypography

object AppTheme {

    /**
     * Get [LocalColors] from the AppColors
     */
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    /**
     * Get [LocalTypography] from the AppTypography
     */
    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    /**
     * Get [LocalDimensions] from the AppDimensions
     */
    val dimensions: AppDimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalDimensions.current

    /**
     * Get [LocalShapes] from the AppShapes
     */
    val shapes: AppShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current

    /**
     * [Einsen] application custom theme
     */
    @Composable
    fun AppTheme(
        colors: AppColors = AppTheme.colors,
        typography: AppTypography = AppTheme.typography,
        dimensions: AppDimensions = AppTheme.dimensions,
        shapes: AppShapes = AppTheme.shapes,
        content: @Composable () -> Unit
    ) {

        val rememberColors = remember {
            colors.copy()
        }.apply {
            updateColorsFrom(colors)
        }

        // / overwrite the existing values here
        CompositionLocalProvider(
            LocalColors provides rememberColors,
            LocalTypography provides typography,
            LocalDimensions provides dimensions,
            LocalShapes provides shapes
        ) {
            content()
        }
    }
}
