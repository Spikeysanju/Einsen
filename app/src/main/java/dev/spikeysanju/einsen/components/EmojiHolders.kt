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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.ui.theme.einsenColors
import dev.spikeysanju.einsen.ui.theme.typography

/**
 * This component helps to show Emoji with Rounded background - larger version.
 * @param emoji
 * @param onSelect
 */
@Composable
fun EmojiPlaceHolder(modifier: Modifier = Modifier, emoji: String, onSelect: () -> Unit) {
    Box(
        modifier = modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(einsenColors.card)
            .clickable { onSelect() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emoji,
            style = typography.h2,
            textAlign = TextAlign.Center,
            color = einsenColors.text
        )
    }
}

/**
 * This component helps to show Emoji with Rounded background - smaller version.
 * @param emoji
 * @param onSelect
 */
@Composable
fun EmojiPlaceHolderSmall(
    modifier: Modifier = Modifier,
    emoji: String,
    onSelect: (String) -> Unit
) {
    Box(
        modifier = modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(einsenColors.card)
            .clickable { onSelect(emoji) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emoji,
            style = typography.h5,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * This component helps to show Emoji with Rounded background - BottomSheet version.
 * @param emoji
 * @param onSelect
 */
@Composable
fun EmojiPlaceHolderBottomSheet(
    modifier: Modifier = Modifier,
    emoji: String,
    onSelect: (String) -> Unit
) {
    Box(
        modifier = modifier
            .size(50.dp)
            .clip(CircleShape)
            .clickable { onSelect(emoji) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emoji,
            style = typography.h5,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(name = "Emoji holders", group = "Emoji")
@Composable
fun EmojiHolderPreview() {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        EmojiPlaceHolder(emoji = "\uD83D\uDD25") {
            // onclick action goes here
        }

        Spacer(modifier = Modifier.height(12.dp))

        EmojiPlaceHolderSmall(emoji = "\uD83D\uDD25") {
            // onclick action goes here
        }

        Spacer(modifier = Modifier.height(12.dp))

        EmojiPlaceHolderBottomSheet(emoji = "\uD83D\uDD25") {
            // onclick action geos here
        }

        Spacer(modifier = Modifier.height(12.dp))

        EmojiTextView(emoji = "\uD83D\uDD25")
    }
}
