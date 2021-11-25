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

package dev.spikeysanju.einsen.ui.theme.typography

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.spikeysanju.einsen.R

val sailec = FontFamily(
    Font(R.font.sailec_regular),
    Font(R.font.sailec_medium, FontWeight.SemiBold),
    Font(R.font.sailec_bold, FontWeight.Bold)
)

data class AppTypography(
    val h1: TextStyle = TextStyle(
        fontFamily = sailec,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
    ),

    val h2: TextStyle = TextStyle(
        fontFamily = sailec,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),

    val body: TextStyle = TextStyle(
        fontFamily = sailec,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),

    val button: TextStyle = TextStyle(
        fontFamily = sailec,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),

    val caption: TextStyle = TextStyle(
        fontFamily = sailec,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),

    val overline: TextStyle = TextStyle(
        fontFamily = sailec,
        fontWeight = FontWeight.SemiBold,
        fontSize = 10.sp
    )

)

val LocalTypography = staticCompositionLocalOf { AppTypography() }
