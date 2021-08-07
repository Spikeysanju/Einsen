package dev.spikeysanju.einsen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.R
import dev.spikeysanju.einsen.ui.theme.typography

@Composable
fun TopBar(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(colors.primary)
            .padding(start = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = title,
            style = typography.h5,
            textAlign = TextAlign.Start,
            color = colors.onPrimary
        )
    }
}

@Composable
fun TopBarWithBack(title: String, upPress: () -> Unit) {
    Column {
        IconButton(onClick = { upPress.invoke() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back Button",
                tint = colors.onPrimary
            )
        }

        Text(
            text = title,
            style = typography.h3,
            textAlign = TextAlign.Start,
            color = colors.onPrimary,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}


@Composable
fun TopBarDetailsWithBack(title: String, upPress: () -> Unit) {
    Row(horizontalArrangement = Arrangement.Center) {
        IconButton(onClick = { upPress.invoke() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back Button",
                tint = colors.onPrimary
            )
        }

        Text(
            text = title,
            style = typography.h3,
            textAlign = TextAlign.Start,
            color = colors.onPrimary,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
