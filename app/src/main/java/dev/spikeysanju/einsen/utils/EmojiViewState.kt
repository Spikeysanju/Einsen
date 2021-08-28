package dev.spikeysanju.einsen.utils

import dev.spikeysanju.einsen.model.EmojiItem

sealed class EmojiViewState {
    // Represents different states for the Emoji
    object Empty : EmojiViewState()
    object Loading : EmojiViewState()
    data class Success(val emojiItem: List<EmojiItem>) : EmojiViewState()
    data class Error(val exception: Throwable) : EmojiViewState()
}
