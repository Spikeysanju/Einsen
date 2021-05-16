package dev.spikeysanju.einsen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.ui.theme.blue200
import dev.spikeysanju.einsen.ui.theme.typography

@Composable
fun LabelView(title: String) {
    Text(text = title, style = typography.caption, textAlign = TextAlign.Start)
}


@Composable
fun InputTextField(title: String) {
    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp),
        value = text,
        onValueChange = { text = it },
        label = { LabelView(title = title) },
        textStyle = typography.body1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            focusedLabelColor = blue200,
            cursorColor = blue200
        )
    )

}