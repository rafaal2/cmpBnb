package org.example.bnb.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Define o esquema de cores para o MODO CLARO
private val LightColorScheme = lightColorScheme(
    primary = Blue,
    secondary = Navy,
    tertiary = Chartreuse,
    background = Color.White,
    surface = Color.White,
)

// Define o esquema de cores para o MODO ESCURO
private val DarkColorScheme = darkColorScheme(
    primary = LightBlue,
    secondary = Navy,
    tertiary = Chartreuse,
    background = Color(0xFF1C1B1F),
    surface = Color(0xFF1C1B1F),
)

// O Composable do Tema principal
@Composable
fun BnbTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Detecta o tema do sistema automaticamente
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}