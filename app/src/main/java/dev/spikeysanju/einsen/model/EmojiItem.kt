package dev.spikeysanju.einsen.model

import com.squareup.moshi.Json

data class EmojiItem(
    @Json(name = "aliases")
    val aliases: List<String>,
    @Json(name = "category")
    val category: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "emoji")
    val emoji: String,
    @Json(name = "ios_version")
    val ios_version: String,
    @Json(name = "skin_tones")
    val skin_tones: Boolean,
    @Json(name = "tags")
    val tags: List<Any>,
    @Json(name = "unicode_version")
    val unicode_version: String
)