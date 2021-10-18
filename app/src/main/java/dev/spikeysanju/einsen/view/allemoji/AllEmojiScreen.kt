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

package dev.spikeysanju.einsen.view.allemoji

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.R
import dev.spikeysanju.einsen.components.EmojiPlaceHolderBottomSheet
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.utils.viewstate.EmojiViewState
import dev.spikeysanju.einsen.view.animationviewstate.AnimationViewState
import dev.spikeysanju.einsen.view.animationviewstate.ScreenState
import dev.spikeysanju.einsen.view.edit.BottomSheetTitle
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun AllEmojiScreen(viewModel: MainViewModel, actions: MainActions, onSelect: (String) -> Unit) {

    // Coroutines scope
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // List, Emoji and Bottom Sheet State
    var emojiState by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    // Emoji List state
    val result = viewModel.emoji.collectAsState().value

    Column {
        BottomSheetTitle()
        LazyVerticalGrid(
            state = listState,
            cells = GridCells.Adaptive(minSize = 60.dp)
        ) {

            // get all emoji
            viewModel.getAllEmoji(context = context)

            // parse emoji into ViewStates
            when (result) {
                EmojiViewState.Empty -> {
                    item {
                        AnimationViewState(
                            title = stringResource(R.string.text_error_title),
                            description = stringResource(R.string.text_error_description),
                            callToAction = stringResource(R.string.text_error_description),
                            screenState = ScreenState.ERROR,
                            actions = {
                                scope.launch {
                                    bottomSheetState.hide()
                                }
                            }
                        )
                    }
                }
                is EmojiViewState.Error -> {
                    item {
                        AnimationViewState(
                            title = stringResource(R.string.text_error_title).plus(", ")
                                .plus(result.exception),
                            description = stringResource(R.string.text_error_description),
                            callToAction = stringResource(R.string.text_error_description),
                            screenState = ScreenState.ERROR,
                            actions = {
                                scope.launch {
                                    bottomSheetState.hide()
                                }
                            }
                        )
                    }
                }
                EmojiViewState.Loading -> {
                    item {
                        AnimationViewState(
                            title = stringResource(R.string.text_error_title),
                            description = stringResource(R.string.text_error_description),
                            callToAction = stringResource(R.string.text_error_description),
                            screenState = ScreenState.LOADING,
                            actions = {
                                scope.launch {
                                    bottomSheetState.hide()
                                }
                            }
                        )
                    }
                }
                is EmojiViewState.Success -> {
                    items(result.emojiItem) { emoji ->
                        EmojiPlaceHolderBottomSheet(
                            emoji = emoji.emoji,
                            onSelect = {
                                emojiState = it
                                onSelect(emojiState)
                            }
                        )
                    }
                }
            }
        }
    }
}
