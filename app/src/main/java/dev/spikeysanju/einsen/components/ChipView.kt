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

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.ui.theme.einsenColors

/**
 * This component helps to show the category for each Task of this app.
 * @param title
 * @param onClick
 */
@Composable
fun ChipView(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .wrapContentWidth()
            .animateContentSize()
            .clickable(onClick = onClick)
            .clip(RoundedCornerShape(12.dp))
            .background(einsenColors.button)
    ) {
        Text(
            text = title, modifier = modifier.padding(12.dp, 6.dp, 12.dp, 6.dp),
            style = MaterialTheme.typography.overline,
            color = einsenColors.white
        )
    }
}

/**
 * This component helps to show the category for each Task of this app.
 * @param title
 * @param onClick
 */
@Composable
fun SmallChipView(
    modifier: Modifier = Modifier,
    title: String,
    color: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .wrapContentWidth()
            .animateContentSize()
            .clickable(onClick = onClick)
            .clip(RoundedCornerShape(12.dp))
            .background(color = color.copy(0.10F))
    ) {
        Text(
            text = title,
            modifier = modifier.padding(start = 6.dp, end = 6.dp, top = 4.dp, bottom = 4.dp),
            style = MaterialTheme.typography.overline,
            color = color
        )
    }
}

@Preview(name = "Category & Tags views", group = "Chip")
@Composable
fun ChipViewPreview() {

    Column {
        SmallChipView(title = "Einsen", color = einsenColors.calm) {
            // onclick action
        }
        Spacer(modifier = Modifier.height(12.dp))

        ChipView(title = "Einsen") {
            // onclick action
        }
    }
}
