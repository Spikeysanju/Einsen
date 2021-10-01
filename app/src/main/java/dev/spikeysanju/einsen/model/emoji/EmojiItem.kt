package dev.spikeysanju.einsen.model.emoji

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "emojis")
@Serializable
data class EmojiItem(
    @NonNull
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "emoji")
    val emoji: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "category")
    val category: String
)
