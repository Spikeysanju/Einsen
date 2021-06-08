package dev.spikeysanju.einsen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.R
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

@Composable
fun PrimaryButtonWithIcon(title: String, onclick: () -> Unit) {

    Row(modifier = Modifier.fillMaxWidth()) {

        Icon(
            painter = painterResource(id = R.drawable.ic_check),
            contentDescription = stringResource(R.string.text_complete_task),
            tint = colors.primary
        )

        Spacer(modifier = Modifier.width(12.dp))


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

}