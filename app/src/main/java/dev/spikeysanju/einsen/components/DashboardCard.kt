package dev.spikeysanju.einsen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.R
import dev.spikeysanju.einsen.ui.theme.typography

// TODO: WRAP PARAMS INTO DATA CLASS
@Composable
fun DashboardCardItem(modifier: Modifier, title: String, count: String, color: Color) {
    val icon = painterResource(id = R.drawable.ic_arrow_right)

    Row(
        modifier = modifier
            .background(color)
            .clickable { }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            modifier = modifier
                .align(Alignment.CenterVertically)
        ) {
            Text(text = title, style = typography.h6, color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = count, style = typography.h2, color = Color.White)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Image(painter = icon, contentDescription = "Arrow right")
    }
}

