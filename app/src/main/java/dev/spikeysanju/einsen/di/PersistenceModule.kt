/*
 *
 *  * Copyright 2021 Spikey Sanju
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     https://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

package dev.spikeysanju.einsen.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.spikeysanju.einsen.data.local.datastore.ThemeManager
import dev.spikeysanju.einsen.data.local.datastore.ThemeManagerImpl
import dev.spikeysanju.einsen.data.local.db.EinsenDatabase
import dev.spikeysanju.einsen.data.local.db.TaskDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
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
