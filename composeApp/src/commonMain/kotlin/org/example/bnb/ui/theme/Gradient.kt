package org.example.bnb.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue

@Composable
fun primaryGradientBrush(): Brush {
    return Brush.horizontalGradient(
        colors = listOf(
            LightGreen, // Cor de baixo
            DarkGreen // Cor de cima
        )
    )
}