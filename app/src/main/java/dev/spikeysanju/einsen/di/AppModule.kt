package dev.spikeysanju.einsen.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.spikeysanju.einsen.data.datastore.ThemeManager
import dev.spikeysanju.einsen.data.datastore.ThemeManagerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providePreferenceManager(application: Application): ThemeManager {
        return ThemeManagerImpl(application)
    }
}