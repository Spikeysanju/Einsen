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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import dev.spikeysanju.einsen.R
import dev.spikeysanju.einsen.components.EinsenInputTextFieldWithoutHint
import dev.spikeysanju.einsen.components.EmojiPlaceHolderBottomSheet
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.utils.viewstate.EmojiViewState
import dev.spikeysanju.einsen.view.animationviewstate.AnimationViewState
import dev.spikeysanju.einsen.view.animationviewstate.ScreenState
import dev.spikeysanju.einsen.view.edit.BottomSheetTitle
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun AllEmojiScreen(
    modifier: Modifier,
    viewModel: MainViewModel,
    actions: MainActions,
    onSelect: (String) -> Unit
) {

    // Coroutines scope
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // Search, List, Emoji and Bottom Sheet State
    var emojiState by remember { mutableStateOf("") }
    var gridSize by remember { mutableStateOf(60.dp) }
    val listState = rememberLazyListState()
    var searchEmoji by remember { mutableStateOf("") }

    // Emoji List state
    val result = viewModel.emoji.collectAsState().value

    LaunchedEffect(
        key1 = Unit,
        block = {
            // log event to firebase
            val allEmojiComposable = bundleOf(
                FirebaseAnalytics.Param.SCREEN_NAME to "All Emoji Screen",
                FirebaseAnalytics.Param.SCREEN_CLASS to "AllEmojiScreen.kt"
            )

            viewModel.firebaseLogEvent("all_emoji_screen", allEmojiComposable)
        }
    )

    Column {

        // Title
        BottomSheetTitle()

        // Search Emoji Input
        EinsenInputTextFieldWithoutHint(
            title = stringResource(R.string.text_search_emoji),
            value = searchEmoji
        ) {
            searchEmoji = it
        }

        Spacer(modifier = modifier.height(12.dp))

        // Emoji Grid List
        LazyVerticalGrid(
            state = listState,
            modifier = modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally),
            cells = GridCells.Adaptive(minSize = gridSize)
        ) {

            // get all emoji
            viewModel.getAllEmoji(context = context, searchQuery = searchEmoji)

            // parse emoji into ViewStates
            when (result) {
                EmojiViewState.Empty -> {
                    // set grid size to 200dp
                    gridSize = 200.dp
                    item {
                        AnimationViewState(
                            title = stringResource(R.string.text_no_emoji_description),
                            description = "Please try searching with another term.\n  Ex -> star_struck",
                            callToAction = stringResource(R.string.text_retry),
                            screenState = ScreenState.ERROR,
                            actions = {
                                scope.launch {
                                    searchEmoji = ""
                                }
                            }
                        )
                    }
                }
                is EmojiViewState.Error -> {
                    gridSize = 200.dp
                    item {
                        AnimationViewState(
                            title = stringResource(R.string.text_error_title),
                            description = stringResource(R.string.text_error_description),
                            callToAction = stringResource(R.string.text_go_back),
                            screenState = ScreenState.ERROR,
                            actions = {
                                scope.launch {
                                    actions.popBackStack.invoke()
                                }
                            }
                        )
                    }
                }
                EmojiViewState.Loading -> {
                    gridSize = 200.dp
                    item {
                        AnimationViewState(
                            title = stringResource(R.string.text_error_title),
                            description = stringResource(R.string.text_error_description),
                            callToAction = stringResource(R.string.text_go_back),
                            screenState = ScreenState.LOADING,
                            actions = {
                                scope.launch {
                                    actions.popBackStack.invoke()
                                }
                            }
                        )
                    }
                }
                is EmojiViewState.Success -> {
                    gridSize = 60.dp
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
