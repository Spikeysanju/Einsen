package dev.spikeysanju.einsen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.ui.theme.einsenColors
import dev.spikeysanju.einsen.ui.theme.typography


/**
 * This customer Label component with helps to show the label for the input field of this app.
 * @param title
 */
@Composable
fun LabelView(title: String) {
    Text(
        text = title,
        style = typography.caption,
        textAlign = TextAlign.Start,
        color = einsenColors.black
    )
}

/**
 * This customer input component which helps to get input data from the users.
 * @param title
 * @param value
 * @param onValueChanged
 */
@Composable
fun InputTextField(title: String, value: String, onValueChanged: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current

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
        colors = textFieldColors(),
        keyboardOptions = KeyboardOptions(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        )
    )
}

@Composable
fun textFieldColors() = TextFieldDefaults.textFieldColors(
    textColor = einsenColors.black,
    focusedLabelColor = einsenColors.black,
    focusedIndicatorColor = einsenColors.black,
    unfocusedIndicatorColor = einsenColors.background,
    cursorColor = einsenColors.black,
    placeholderColor = einsenColors.black,
    disabledPlaceholderColor = einsenColors.black
)
