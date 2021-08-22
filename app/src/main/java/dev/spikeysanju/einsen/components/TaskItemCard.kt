package dev.spikeysanju.einsen.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.shapes
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.spikeysanju.einsen.model.Task
import dev.spikeysanju.einsen.ui.theme.Avenir
import dev.spikeysanju.einsen.ui.theme.myColors
import dev.spikeysanju.einsen.ui.theme.typography


@ExperimentalFoundationApi
@Composable
fun TaskItemCard(
    task: Task,
    onClick: () -> Unit,
    onCheckboxChange: (Boolean) -> Unit
) {

    Spacer(modifier = Modifier.height(12.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // checkbox state
        val status = remember { mutableStateOf(task.isCompleted) }

        // Checkbox
        EisenCheckBox(value = status.value, onValueChanged = {
            status.value = it
            onCheckboxChange(status.value)

        })

        Spacer(modifier = Modifier.width(12.dp))
        // Emoji + (title + category)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = shapes.large)
                .background(myColors.card)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            // Emoji Text View
            EmojiTextView(emoji = task.emoji)
            Spacer(modifier = Modifier.width(12.dp))

            // condition check if the task is marked as completed the make it as strikethrough
            val titleStyle = when (task.isCompleted) {
                true -> TextStyle(
                    textDecoration = TextDecoration.LineThrough,
                    fontSize = 16.sp,
                    fontFamily = Avenir,
                    fontWeight = FontWeight.SemiBold
                )
                false -> typography.subtitle1
            }

            val categoryStyle = when (task.isCompleted) {
                true -> TextStyle(
                    textDecoration = TextDecoration.LineThrough,
                    fontSize = 12.sp,
                    fontFamily = Avenir,
                    fontWeight = FontWeight.Normal
                )
                false -> typography.caption
            }

            // Title + Content
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
            ) {
                Text(
                    text = task.title,
                    style = titleStyle,
                    color = myColors.day,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = task.category,
                    style = categoryStyle,
                    color = myColors.day.copy(.7f)
                )
            }
        }
    }
}


@Composable
fun EmojiTextView(emoji: String) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .padding(12.dp)
            .clip(CircleShape)
            .clickable { }
            .background(myColors.bg)
    ) {
        Text(
            text = emoji,
            color = myColors.day,
            style = typography.subtitle1,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun EisenCheckBox(value: Boolean, onValueChanged: (Boolean) -> Unit) {
    Checkbox(
        checked = value, onCheckedChange = {
            onValueChanged(it)
        }, colors = CheckboxDefaults.colors(
            colors.onPrimary,
            colors.onPrimary.copy(0.3F)
        )
    )
}
