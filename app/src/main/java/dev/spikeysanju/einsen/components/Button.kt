package dev.spikeysanju.einsen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.ui.theme.Shapes
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
fun PrimaryButtonWithIcons(title: String, icon: Painter, onclick: () -> Unit) {

    Row(
        modifier = Modifier
            .wrapContentWidth()
            .height(48.dp)
            .clickable { onclick() }
            .clip(Shapes.large)
            .background(myColors.button)
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = icon,
            contentDescription = title,
            tint = myColors.white
        )

        Spacer(modifier = Modifier.width(12.dp))

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
    iconTint: Color,
    onclick: () -> Unit,
    color: Color
) {
    Button(
        onClick = { onclick() }, colors = ButtonDefaults.buttonColors(
            backgroundColor = color,
            contentColor = myColors.white
        )
    ) {
        Icon(
            painter = icon,
            contentDescription = title,
            tint = iconTint
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = title,
            style = typography.subtitle2,
            textAlign = TextAlign.Center,
            color = iconTint
        )
    }
}