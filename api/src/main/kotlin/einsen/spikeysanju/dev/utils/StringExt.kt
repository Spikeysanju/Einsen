package einsen.spikeysanju.dev.utils

fun String.containsSpecialCharacters() = !(matches("[a-zA-Z0-9]+".toRegex()))

fun String.containsOnlyNumbers() = (matches("[0-9]+".toRegex()))
