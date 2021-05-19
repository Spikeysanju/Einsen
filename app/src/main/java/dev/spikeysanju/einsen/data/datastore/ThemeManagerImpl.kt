package dev.spikeysanju.einsen.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Singleton

val Context.themePrefDataStore by preferencesDataStore("ui_mode_pref")

class ThemeManagerImpl(context: Context) : ThemeManager {

    private val dataStore = context.themePrefDataStore

    override val uiModeFlow: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preference -> preference[IS_DARK_MODE] ?: false }

    override suspend fun setDarkMode(enable: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = enable
        }
    }

    companion object {
        val IS_DARK_MODE = booleanPreferencesKey("dark_mode")
    }
}

@Singleton
interface ThemeManager {
    val uiModeFlow: Flow<Boolean>
    suspend fun setDarkMode(enable: Boolean)
}