package dev.spikeysanju.einsen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.R
import dev.spikeysanju.einsen.ui.theme.myColors
import dev.spikeysanju.einsen.ui.theme.typography

@Composable
fun TopBar(title: String, onToggle: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                textAlign = TextAlign.Start,
                style = typography.h5,
                color = myColors.black
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 24.dp, 36.dp, 0.dp),
            horizontalArrangement = Arrangement.End
        ) {
            EinsenThemeSwitch(onToggle = { onToggle() })
        }
    }
}

@Composable
fun EinsenThemeSwitch(onToggle: () -> Unit) {

    val icon = if (isSystemInDarkTheme())
        painterResource(id = R.drawable.ic_bulb_off)
    else
        painterResource(id = R.drawable.ic_bulb_on)

    Icon(
        painter = icon,
        contentDescription = null,
        modifier = Modifier
            .size(24.dp)
            .clickable(onClick = onToggle),
        tint = myColors.black
    )
}

@Composable
fun TopBarWithBack(title: String, upPress: () -> Unit) {
    Row(
        modifier = Modifier.padding(start = 12.dp, top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = { upPress.invoke() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = stringResource(R.string.back_button),
                tint = myColors.black
            )
        }

        Text(
            text = title,
            style = typography.h6,
            textAlign = TextAlign.Start,
            color = myColors.black,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
