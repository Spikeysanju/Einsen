package dev.spikeysanju.einsen.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dev.spikeysanju.einsen.di.AppModule
import dev.spikeysanju.einsen.model.emoji.EmojiItem
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class EmojiDatabaseWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    private val TAG by lazy { EmojiDatabaseWorker::class.java.simpleName }

    override suspend fun doWork(): Result = coroutineScope {

        try {
            // read JSON file
            val myJson = applicationContext.assets.open("emoji.json").bufferedReader().use {
                it.readText()
            }

            // format JSON
            val format = Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            }

            val decodedEmoji = format.decodeFromString<List<EmojiItem>>(myJson)
            val database = AppModule.provideAppDatabase(applicationContext)
            database.getEmojisDao().insertEmojis(decodedEmoji)
            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Error inserting emoji", e)
            Result.failure()
        }
    }
}
