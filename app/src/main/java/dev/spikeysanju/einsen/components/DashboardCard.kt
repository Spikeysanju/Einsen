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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import dev.spikeysanju.einsen.ui.theme.apptheme.AppTheme
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
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    count: String,
    color: Color,
    onClick: () -> Unit
) {
    val gradientBrush = Brush.verticalGradient(listOf(color.copy(.8F), color), startY = 10F)

    Spacer(modifier = modifier.height(AppTheme.dimensions.paddingXXL))

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = AppTheme.dimensions.paddingXL, end = AppTheme.dimensions.paddingXL)
            .coloredShadow(
                color,
                alpha = 0.4F,
                borderRadius = AppTheme.dimensions.paddingXXL,
                shadowRadius = AppTheme.dimensions.paddingMedium,
                offsetX = AppTheme.dimensions.paddingNone,
                offsetY = AppTheme.dimensions.paddingSmall
            )
            .clip(RoundedCornerShape(AppTheme.shapes.shapeXL))
            .background(brush = gradientBrush)
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            modifier = modifier
                .wrapContentWidth()
                .padding(
                    top = AppTheme.dimensions.paddingXXXL,
                    bottom = AppTheme.dimensions.paddingXXXL
                )
                .align(Alignment.CenterVertically)
        ) {
            Text(text = title, style = AppTheme.typography.h2, color = Color.White)
            Spacer(modifier = modifier.height(AppTheme.dimensions.paddingXL))
            Text(text = description, style = AppTheme.typography.subtitle, color = Color.White)
        }
        Spacer(modifier = modifier.height(AppTheme.dimensions.paddingXL))
        Text(text = count, style = AppTheme.typography.bigTitle, color = Color.White)
    }
}

@Preview(name = "Dashboard Eisenhower matrix cards ", group = "Card")
@Composable
fun DashboardPreview() {
    Column {
        InfoCard(title = "Urgency", value = "2", modifier = Modifier)

        DashboardCardItem(
            title = "Do it now",
            description = "Important & Urgent",
            count = "13",
            color = AppTheme.colors.success
        ) {
            // click action goes here
        }
        Spacer(modifier = Modifier.height(AppTheme.dimensions.paddingLarge))
        DashboardCardItem(
            title = "Decide when to do",
            description = "Important not Urgent",
            count = "17",
            color = AppTheme.colors.information
        ) {
            // click action goes here
        }

        Spacer(modifier = Modifier.height(AppTheme.dimensions.paddingLarge))
        DashboardCardItem(
            title = "Delegate it",
            description = "Urgent not Important",
            count = "03",
            color = AppTheme.colors.error
        ) {
            // click action goes here
        }

        Spacer(modifier = Modifier.height(AppTheme.dimensions.paddingLarge))
        DashboardCardItem(
            title = "Dump it",
            description = "Not Important & Not Urgent",
            count = "11",
            color = AppTheme.colors.warning
        ) {
            // click action goes here
        }
    }
}
