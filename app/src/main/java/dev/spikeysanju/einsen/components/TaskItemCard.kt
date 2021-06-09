package dev.spikeysanju.einsen.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.model.Task
import dev.spikeysanju.einsen.ui.theme.typography
import dev.spikeysanju.einsen.ui.theme.white


@ExperimentalFoundationApi
@Composable
fun TaskItemCard(
    task: Task,
    onClick: () -> Unit,
    onDoubleTap: () -> Unit,
    onLongClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(12.dp))
    // Emoji + (title + category)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = shapes.large)
            .background(colors.onSecondary)
            .combinedClickable(onClick = {
                onClick()   // go to task details
            }, onDoubleClick = {
                onDoubleTap()   // delete task
            }, onLongClick = {
                onLongClick()   // show bottom sheet
            }),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // Emoji Text View
        EmojiTextView(emoji = task.emoji)
        Spacer(modifier = Modifier.width(12.dp))

        // Title + Content
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically),
        ) {
            Text(
                text = task.title,
                style = typography.subtitle1,
                color = colors.onPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = task.category,
                style = typography.caption,
                color = colors.onPrimary.copy(.7f)
            )
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
            .background(colors.background)
    ) {
        Text(
            text = emoji,
            color = white,
            style = typography.subtitle1,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}