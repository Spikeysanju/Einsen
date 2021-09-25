package dev.spikeysanju.einsen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.ui.theme.einsenColors
import dev.spikeysanju.einsen.ui.theme.typography

/**
 * This customer Label component with helps to show the label for the input field of this app.
 * @param title
 */

@Composable
fun EinsenLabelView(title: String) {
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
 * @param onValueChanged
 */
@Stable
@Composable
fun EinsenInputTextField(title: String, value: String, onValueChanged: (String) -> Unit) {
    var textState by rememberSaveable { mutableStateOf("") }
    var errorState by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Column(horizontalAlignment = Alignment.Start) {

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            value = value,
            onValueChange = {
                textState = it
                when (textState.isEmpty()) {
                    true -> {
                        errorState = true
                        errorMessage = "$title should not be empty"
                    }
                    false -> {
                        errorState = false
                        errorMessage = ""
                    }
                }
                onValueChanged(it)
            },

            label = { EinsenLabelView(title = title) },
            textStyle = typography.body1,
            colors = textFieldColors(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {

                    // Focus to next input
                    focusManager.moveFocus(FocusDirection.Next)

                    // validate
                    when (textState.isEmpty()) {
                        true -> {
                            errorState = true
                            errorMessage = "$title should not be empty"
                        }
                        false -> {
                            errorState = false
                            errorMessage = ""
                        }
                    }
                }
            ),
            isError = errorState
        )

        if (errorState) {
            Text(
                errorMessage,
                style = typography.caption,
                color = einsenColors.err,
                modifier = Modifier.padding(top = 12.dp, start = 16.dp, end = 16.dp)
            )
        }
    }
}

@Composable
fun textFieldColors() = TextFieldDefaults.textFieldColors(
    textColor = einsenColors.black,
    focusedLabelColor = einsenColors.black,
    focusedIndicatorColor = einsenColors.black,
    unfocusedIndicatorColor = einsenColors.background,
    cursorColor = einsenColors.black,
    backgroundColor = einsenColors.card,
    placeholderColor = einsenColors.black,
    disabledPlaceholderColor = einsenColors.black
)
