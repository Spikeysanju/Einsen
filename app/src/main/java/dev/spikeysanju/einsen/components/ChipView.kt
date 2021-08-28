package dev.spikeysanju.einsen.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.ui.theme.myColors

@Composable
fun ChipView(title: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .animateContentSize()
            .clickable(onClick = onClick)
            .clip(RoundedCornerShape(12.dp))
            .background(myColors.button)
    ) {
        Text(
            text = title, modifier = Modifier.padding(12.dp, 6.dp, 12.dp, 6.dp),
            style = MaterialTheme.typography.overline,
            color = myColors.white
        )
    }
}
