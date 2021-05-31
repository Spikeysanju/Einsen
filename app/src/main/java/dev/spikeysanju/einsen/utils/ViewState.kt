package dev.spikeysanju.einsen.utils

import dev.spikeysanju.einsen.model.Task

sealed class ViewState {
    // Represents different states for the LatestNews screen
    object Empty : ViewState()
    object Loading : ViewState()
    data class Success(val task: List<Task>) : ViewState()
    data class Error(val exception: Throwable) : ViewState()
}
