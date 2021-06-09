package dev.spikeysanju.einsen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.ui.theme.typography

@Composable
fun PrimaryButton(title: String, onclick: () -> Unit) {

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .padding(start = 20.dp, end = 20.dp),
        onClick = { onclick() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colors.onPrimary,
            contentColor = colors.primary
        )
    ) {
        Text(
            text = title,
            style = typography.subtitle2,
            textAlign = TextAlign.Center,
            color = colors.primary
        )
    }
}