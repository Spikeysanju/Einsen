package dev.spikeysanju.einsen.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.spikeysanju.einsen.data.datastore.UIModeDataStore
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    // init ThemeModeDataStore
    private val uiModeDataStore = UIModeDataStore(application)

    // get ui mode
    val getUIMode = uiModeDataStore.theme

    // save theme mode
    fun setUIMode(isDarkMode: Boolean) = viewModelScope.launch {
        uiModeDataStore.saveThemeMode(isDarkMode)
    }
}