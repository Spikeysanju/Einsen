package dev.spikeysanju.einsen.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class EmojiItem(
    val emoji: String,
    val description: String,
    val category: String,
    val aliases: List<String> = listOf(""),
    val tags: List<String> = listOf(""),
    @Transient val unicode_version: String = "",
    @Transient val ios_version: String = "",
    @Transient val skin_tones: Boolean = false
)