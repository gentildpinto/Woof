package me.gentilpinto.woof.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColorScheme(
    background = Cyan900,
    surface = Cyan700,
    onSurface = White,
    primary = Grey900,
    onPrimary = White,
    secondary = Grey100

)

private val LightColorPalette = lightColorScheme(
    background = Green100,
    surface = Green50,
    onSurface = Grey900,
    primary = Grey50,
    onPrimary = Grey900,
    secondary = Grey700
)

@Composable
fun WoofTheme(isDarkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = when (isDarkTheme) {
        true -> DarkColorPalette
        false -> LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors, typography = Typography, shapes = Shapes, content = content
    )
}