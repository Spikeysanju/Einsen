package dev.spikeysanju.einsen.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.spikeysanju.einsen.R

val Grotesk = FontFamily(
    Font(R.font.grotesk_regular),
    Font(R.font.grotesk_medium, FontWeight.W500),
    Font(R.font.grotesk_bold, FontWeight.Bold)
)

val Avenir = FontFamily(
    Font(R.font.avenir_medium),
    Font(R.font.avenir_semibold, FontWeight.SemiBold),
    Font(R.font.avenir_bold, FontWeight.Bold)
)

// Set of Material typography styles to start with
val typography = Typography(

    h1 = TextStyle(
        fontFamily = Avenir,
        fontWeight = FontWeight.Bold,
        fontSize = 80.sp
    ),

    h2 = TextStyle(
        fontFamily = Avenir,
        fontWeight = FontWeight.Bold,
        fontSize = 48.sp
    ),
    h3 = TextStyle(
        fontFamily = Avenir,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp
    ),
    h4 = TextStyle(
        fontFamily = Avenir,
        fontWeight = FontWeight.W600,
        fontSize = 30.sp
    ),
    h5 = TextStyle(
        fontFamily = Avenir,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    h6 = TextStyle(
        fontFamily = Avenir,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = Avenir,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = Avenir,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    body1 = TextStyle(
        fontFamily = Avenir,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    body2 = TextStyle(
        fontFamily = Avenir,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    button = TextStyle(
        fontFamily = Avenir,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = Avenir,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    overline = TextStyle(
        fontFamily = Avenir,
        fontWeight = FontWeight.W500,
        fontSize = 12.sp

    )
)