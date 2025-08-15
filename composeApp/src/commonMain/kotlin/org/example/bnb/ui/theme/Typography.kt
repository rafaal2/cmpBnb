package org.example.bnb.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Agora, nosso AppTypography vai usar a família de fontes Poppins
@Composable
fun AppTypography(): Typography {
    val poppins = poppinsFontFamily() // 1. Pega a nossa família de fontes

    return Typography( // 2. Usa 'poppins' em vez de 'FontFamily.Default'
        bodyLarge = TextStyle(
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        titleLarge = TextStyle(
            fontFamily = poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        ),
        labelSmall = TextStyle(
            fontFamily = poppins,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp
        )
    )
}