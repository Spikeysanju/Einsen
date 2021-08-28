package dev.spikeysanju.einsen.utils

sealed class CountViewState {
    // Represents different states for the Emoji
    object Empty : CountViewState()
    object Loading : CountViewState()
    data class Success(val count: Int) : CountViewState()
    data class Error(val exception: Throwable) : CountViewState()
}
