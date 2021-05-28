package dev.spikeysanju.einsen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.ui.theme.typography

@Composable
fun LabelView(title: String) {
    Text(
        text = title,
        style = typography.caption,
        textAlign = TextAlign.Start,
        color = colors.onPrimary
    )
}


@Composable
fun InputTextField(title: String, value: String, onValueChanged: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp),
        value = value,
        onValueChange = {
            onValueChanged(it)
        },

        label = { LabelView(title = title) },
        textStyle = typography.body1,
        colors = textFieldColors()
    )

}

@Composable
fun textFieldColors() = TextFieldDefaults.textFieldColors(
    textColor = MaterialTheme.colors.onPrimary,
    focusedLabelColor = MaterialTheme.colors.onPrimary,
    focusedIndicatorColor = MaterialTheme.colors.primary,
    unfocusedIndicatorColor = MaterialTheme.colors.secondaryVariant,
    cursorColor = MaterialTheme.colors.onPrimary,
    placeholderColor = MaterialTheme.colors.primaryVariant,
    disabledPlaceholderColor = MaterialTheme.colors.secondary
)