package dev.spikeysanju.einsen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun InputTextField(title: String) {
    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp),
        value = text,
        onValueChange = {
            text = it
        },

        label = { LabelView(title = title) },
        textStyle = typography.body1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = colors.onPrimary,
            focusedLabelColor = colors.onPrimary,
            focusedIndicatorColor = colors.primary,
            unfocusedIndicatorColor = colors.secondaryVariant,
            cursorColor = colors.onPrimary,
            placeholderColor = colors.primaryVariant,
            disabledPlaceholderColor = colors.secondary
        )
    )

}