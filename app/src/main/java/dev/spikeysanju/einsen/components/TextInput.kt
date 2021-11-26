/*
 *
 *  * Copyright 2021 Spikey Sanju
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     https://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

package dev.spikeysanju.einsen.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import dev.spikeysanju.einsen.ui.theme.apptheme.AppTheme

/**
 * This customer Label component with helps to show the label for the input field of this app.
 * @param title
 */

@Composable
fun EinsenLabelView(title: String) {
    Text(
        text = title,
        style = AppTheme.typography.caption,
        textAlign = TextAlign.Start,
        color = AppTheme.colors.text
    )
}

/**
 * This customer input component which helps to get input data from the users.
 * @param title
 * @param onValueChanged
 */
@Stable
@Composable
fun EinsenInputTextField(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    onValueChanged: (String) -> Unit
) {
    var textState by rememberSaveable { mutableStateOf("") }
    var errorState by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Column(horizontalAlignment = Alignment.Start) {

        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    start = AppTheme.dimensions.paddingXL,
                    end = AppTheme.dimensions.paddingXL
                ),
            value = value,
            readOnly = readOnly,
            enabled = enabled,
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
            textStyle = AppTheme.typography.body,
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
                style = AppTheme.typography.caption,
                color = AppTheme.colors.error,
                modifier = modifier.padding(
                    top = AppTheme.dimensions.paddingLarge,
                    start = AppTheme.dimensions.paddingXL,
                    end = AppTheme.dimensions.paddingLarge
                )
            )
        }
    }
}

@Stable
@Composable
fun EinsenInputTextFieldWithoutHint(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    onValueChanged: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column(horizontalAlignment = Alignment.Start) {

        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    start = AppTheme.dimensions.paddingXL,
                    end = AppTheme.dimensions.paddingXL
                ),
            value = value,
            onValueChange = {
                onValueChanged(it)
            },

            label = { EinsenLabelView(title = title) },
            textStyle = AppTheme.typography.body,
            colors = textFieldColors(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onNext = {

                    // Focus to next input
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        )
    }
}

@Composable
fun textFieldColors() = TextFieldDefaults.textFieldColors(
    textColor = AppTheme.colors.text,
    focusedLabelColor = AppTheme.colors.text,
    focusedIndicatorColor = AppTheme.colors.text,
    unfocusedIndicatorColor = AppTheme.colors.card,
    cursorColor = AppTheme.colors.text,
    backgroundColor = AppTheme.colors.card,
    placeholderColor = AppTheme.colors.card,
    disabledPlaceholderColor = AppTheme.colors.card
)

@Preview(name = "Text Input Field", group = "Input", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TextInputPreview() {
    Column {
        EinsenInputTextField(
            title = "Title", value = "Einsen Architecture",
            onValueChanged = {
                // value change action goes here
            }
        )

        Spacer(modifier = Modifier.height(AppTheme.dimensions.paddingLarge))

        EinsenInputTextFieldWithoutHint(
            title = "Description", value = "Einsen Architecture is really cool!",
            onValueChanged = {
                // value change action goes here
            }
        )
    }
}
