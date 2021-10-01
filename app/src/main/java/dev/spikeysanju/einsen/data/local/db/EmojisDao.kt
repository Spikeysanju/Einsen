package dev.spikeysanju.einsen.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.spikeysanju.einsen.model.emoji.EmojiItem
import kotlinx.coroutines.flow.Flow

@Dao
interface EmojisDao {

    @Query("SELECT * FROM emojis")
    fun getAllEmojis(): Flow<List<EmojiItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmojis(emojiItem: List<EmojiItem>)

    @Query("DELETE FROM emojis")
    suspend fun deleteAllEmojis()

}
