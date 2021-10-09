package dev.spikeysanju.einsen.view.allemoji

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
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

    // Emoji and bottom sheet state
    var emojiState by remember { mutableStateOf("") }
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    // Emoji List state
    val result = viewModel.emoji.collectAsState().value

    Column {
        BottomSheetTitle()
        LazyVerticalGrid(
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


