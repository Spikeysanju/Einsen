package dev.spikeysanju.einsen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.ui.theme.einsenColors
import dev.spikeysanju.einsen.ui.theme.typography

/**
 * This component helps to show Emoji with Rounded background - larger version.
 * @param emoji
 * @param onSelect
 */
@Composable
fun EmojiPlaceHolder(emoji: String, onSelect: () -> Unit) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .clickable { onSelect() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emoji,
            style = typography.h1,
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
fun EmojiPlaceHolderSmall(emoji: String, onSelect: (String) -> Unit) {
    Box(
        modifier = Modifier
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
