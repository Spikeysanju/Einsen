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

package dev.spikeysanju.einsen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.ui.theme.typography
import dev.spikeysanju.einsen.utils.coloredShadow

/**
 * This component helps to show the priority of the task with title, color & task count.
 * @param title
 * @param description
 * @param count
 * @param color
 * @param onClick
 */
@Composable
fun DashboardCardItem(
    title: String,
    description: String,
    count: String,
    color: Color,
    onClick: () -> Unit
) {
    val gradientBrush = Brush.verticalGradient(listOf(color.copy(.8F), color), startY = 10F)

    Spacer(modifier = Modifier.height(24.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .coloredShadow(
                color,
                alpha = 0.4F,
                borderRadius = 10.dp,
                shadowRadius = 24.dp,
                offsetX = 0.dp,
                offsetY = 4.dp
            )
            .clip(RoundedCornerShape(24.dp))
            .background(brush = gradientBrush)
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .padding(top = 36.dp, bottom = 36.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(text = title, style = typography.h6, color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = description, style = typography.subtitle1, color = Color.White)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = count, style = typography.h2, color = Color.White)
    }
}
