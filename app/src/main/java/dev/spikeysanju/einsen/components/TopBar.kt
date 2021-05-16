package dev.spikeysanju.einsen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import dev.spikeysanju.einsen.R
import dev.spikeysanju.einsen.ui.theme.typography

@Composable
fun TopBar(title: String) {
    Column {
        IconButton(onClick = { /*TODO*/ }) {
            painterResource(id = R.drawable.ic_back)
        }

        Text(text = title, style = typography.h4, textAlign = TextAlign.Start, color = Color.White)
    }
}
