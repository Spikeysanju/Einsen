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
