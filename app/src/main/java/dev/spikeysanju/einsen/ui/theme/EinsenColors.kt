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

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

data class EinsenColors(
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
    val onSecondary: Color get() = material.onSecondary
    val onError: Color get() = material.onError
}
