package dev.spikeysanju.einsen.utils.viewstate

sealed class CountViewState {
    // Represents different states for the Dashboard Task Count
    object Empty : CountViewState()
    object Loading : CountViewState()
    data class Success(val count: Int) : CountViewState()
    data class Error(val exception: Throwable) : CountViewState()
}
