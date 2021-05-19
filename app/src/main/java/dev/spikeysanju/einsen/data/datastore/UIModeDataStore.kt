package dev.spikeysanju.einsen.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


abstract class PrefsDataStore(context: Context, fileName: String) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(fileName)
    val mDataStore: DataStore<Preferences> = context.dataStore
}

class UIModeDataStore(context: Context) : PrefsDataStore(context = context, PREF_FILE_UI_MODE),
    UIModeImpl {

    // used to get the data from datastore
    override val theme: Flow<Boolean>
        get() = mDataStore.data.map { preferences ->
            val themeStyle = preferences[UI_MODE_KEY] ?: false
            themeStyle
        }

    // used to save the ui preference to datastore
    override suspend fun saveThemeMode(isDarkMode: Boolean) {
        mDataStore.edit { preferences ->
            preferences[UI_MODE_KEY] = isDarkMode
        }
    }


    companion object {
        private const val PREF_FILE_UI_MODE = "ui_mode_preference"
        private val UI_MODE_KEY = booleanPreferencesKey("ui_mode")
    }
}

interface UIModeImpl {
    val theme: Flow<Boolean>
    suspend fun saveThemeMode(isDarkMode: Boolean)
}