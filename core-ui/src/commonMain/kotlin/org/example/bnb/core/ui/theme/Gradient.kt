package org.example.bnb.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue

@Composable
fun primaryGradientBrush(): Brush {
    return Brush.verticalGradient(
        colors = listOf(
            Blue, // Cor de cima
            DarkBlue // Cor de baixo
        )
    )
}