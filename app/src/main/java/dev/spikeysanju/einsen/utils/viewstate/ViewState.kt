package dev.spikeysanju.einsen.utils.viewstate

import dev.spikeysanju.einsen.model.task.Task

sealed class ViewState {
    // Represents different states for the All Task screen
    object Empty : ViewState()
    object Loading : ViewState()
    data class Success(val task: List<Task>) : ViewState()
    data class Error(val exception: Throwable) : ViewState()
}
