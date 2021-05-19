package dev.spikeysanju.einsen.model


data class MyTheme(val myThemeStyle: ThemeStyle)

enum class ThemeStyle(name: String) {
    BLUE("Blue"),
    GREEN("Green"),
    GRAY("Gray"),
    OLIVER("Oliver"),
    PURPLE("Purple"),
    RED("Red")
}