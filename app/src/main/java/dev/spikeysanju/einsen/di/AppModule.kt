package dev.spikeysanju.einsen.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.spikeysanju.einsen.data.datastore.ThemeManager
import dev.spikeysanju.einsen.data.datastore.ThemeManagerImpl
import dev.spikeysanju.einsen.data.datastore.db.EinsenDatabase
import dev.spikeysanju.einsen.data.datastore.db.TaskDao
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providePreferenceManager(@ApplicationContext context: Context): ThemeManager {
        return ThemeManagerImpl(context = context)
    }

    @Singleton
    @Provides
    fun provideTaskDao(database: EinsenDatabase): TaskDao = database.getTaskDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): EinsenDatabase =
        Room.databaseBuilder(
            context,
            EinsenDatabase::class.java,
            "task-db"
        ).fallbackToDestructiveMigration().build()
}