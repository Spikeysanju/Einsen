package dev.spikeysanju.einsen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.ui.theme.myColors
import dev.spikeysanju.einsen.ui.theme.typography

@Composable
fun PrimaryButton(title: String, onclick: () -> Unit) {

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .clip(RoundedCornerShape(60.dp))
            .padding(start = 20.dp, end = 20.dp),
        onClick = { onclick() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = myColors.button,
            contentColor = myColors.white
        ),
    ) {
        Text(
            text = title,
            style = typography.subtitle2,
            textAlign = TextAlign.Center,
            color = myColors.white
        )
    }
}

@Composable
fun PrimaryButtonWithIcon(
    title: String,
    icon: Painter,
    onclick: () -> Unit,
    color: Color
) {
    Button(
        onClick = { onclick() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = color,
            contentColor = Color.White
        )
    ) {
        Icon(
            painter = icon,
            contentDescription = title,
            tint = Color.White
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = title,
            style = typography.subtitle2,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}
