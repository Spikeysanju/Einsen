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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.ui.theme.einsenColors
import dev.spikeysanju.einsen.ui.theme.typography

@Composable
fun ItemWorkspaceCard(
    modifier: Modifier,
    title: String,
    itemCount: String,
    emoji: String,
    tag: String,
    onCardClick: () -> Unit,
    onChipViewClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable {
                onCardClick()
            },
        backgroundColor = einsenColors.card, shape = RoundedCornerShape(24.dp), elevation = 0.dp
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(24.dp), horizontalAlignment = Alignment.Start
        ) {
            EmojiTextView(emoji = emoji)
            Spacer(modifier = modifier.height(12.dp))
            Text(text = title, style = typography.h6, color = einsenColors.black)
            Spacer(modifier = modifier.height(12.dp))
            Text(text = itemCount, style = typography.caption, color = einsenColors.black)
            Spacer(modifier = modifier.height(16.dp))
            SmallChipView(title = tag, color = einsenColors.calm) {
                onChipViewClick()
            }
        }
    }
}

@Preview(name = "Projects Card", group = "Card")
@Composable
fun ProjectCardPreview() {
    ItemWorkspaceCard(
        modifier = Modifier,
        title = "Einsen app",
        itemCount = "5 action items",
        emoji = "\uD83D\uDD25",
        tag = "Open source",
        onCardClick = {
            // on card click action goes here
        }
    ) {

        // on tag click action goes here
    }
}
