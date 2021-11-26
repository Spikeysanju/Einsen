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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.spikeysanju.einsen.model.task.Priority
import dev.spikeysanju.einsen.model.task.Task
import dev.spikeysanju.einsen.ui.theme.apptheme.AppTheme
import dev.spikeysanju.einsen.ui.theme.typography.sailec

/**
 * This component is used to show all the task item of this app.
 * @param task
 * @param onClick
 * @param onCheckboxChange
 */

@Composable
fun TaskItemCard(
    modifier: Modifier = Modifier,
    task: Task,
    onClick: () -> Unit,
    onCheckboxChange: (Boolean) -> Unit
) {

    Spacer(modifier = modifier.height(AppTheme.dimensions.paddingLarge))
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // checkbox state
        val status = remember { mutableStateOf(task.isCompleted) }

        /**
         * Checkbox
         */
        EisenCheckBox(
            value = status.value,
            onValueChanged = {
                status.value = it
                onCheckboxChange(status.value)
            }
        )

        Spacer(modifier = modifier.width(AppTheme.dimensions.paddingLarge))

        /**
         * Emoji + (title + category)
         */
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(AppTheme.dimensions.paddingLarge))
                .background(AppTheme.colors.card)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            /**
             * Emoji Text View
             */
            EmojiTextView(emoji = task.emoji)
            Spacer(modifier = modifier.width(AppTheme.dimensions.paddingLarge))

            /**
             * Title + category
             */
            Column(
                modifier = modifier
                    .align(Alignment.CenterVertically),
            ) {
                Text(
                    text = task.title,
                    style = when (task.isCompleted) {
                        true -> TextStyle(
                            textDecoration = TextDecoration.LineThrough,
                            fontSize = 16.sp,
                            fontFamily = sailec,
                            fontWeight = FontWeight.SemiBold
                        )
                        false -> AppTheme.typography.subtitle
                    },
                    color = AppTheme.colors.text,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = modifier.height(AppTheme.dimensions.paddingLarge))

                Text(
                    text = task.category,
                    style = when (task.isCompleted) {
                        true -> TextStyle(
                            textDecoration = TextDecoration.LineThrough,
                            fontSize = 12.sp,
                            fontFamily = sailec,
                            fontWeight = FontWeight.Normal
                        )
                        false -> AppTheme.typography.caption
                    },
                    color = AppTheme.colors.text.copy(.7f)
                )
            }
        }
    }
}

/**
 * This component helps to show Emoji with Rounded background.
 * @param emoji
 */
@Composable
fun EmojiTextView(modifier: Modifier = Modifier, emoji: String) {
    Box(
        modifier = modifier
            .size(80.dp)
            .padding(AppTheme.dimensions.paddingLarge)
            .clip(CircleShape)
            .clickable { }
            .background(AppTheme.colors.background)
    ) {
        Text(
            text = emoji,
            color = AppTheme.colors.text,
            style = AppTheme.typography.subtitle,
            textAlign = TextAlign.Center,
            modifier = modifier.align(Alignment.Center)
        )
    }
}

/**
 * This customer slider component helps to show the slider with step indicator.
 * @param value
 * @param onValueChanged
 */
@Composable
fun EisenCheckBox(value: Boolean, onValueChanged: (Boolean) -> Unit) {
    Checkbox(
        checked = value,
        onCheckedChange = {
            onValueChanged(it)
        },
        colors = CheckboxDefaults.colors(
            uncheckedColor = AppTheme.colors.primary,
            checkedColor = AppTheme.colors.primary,
            checkmarkColor = AppTheme.colors.white
        )
    )
}

@Preview(name = "Task Item Card", group = "Card")
@Composable
fun TaskItemCardPreview() {
    TaskItemCard(
        task = Task(
            "Einsen Architecture",
            "Einsen app architecture demo works needs to be done on coming monday!",
            "Android",
            "\uD83D\uDD25",
            3,
            4,
            Priority.IMPORTANT,
            "18/12/1998",
            false,
            2,
            4,
            1,
        ),
        onClick = {
            //  on click action goes here
        },
        onCheckboxChange = {
            // check box change logic goes here
        }
    )
}
