package org.example.bnb.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import bnb.composeapp.generated.resources.Res
import bnb.composeapp.generated.resources.*
import org.jetbrains.compose.resources.Font

@Composable
fun poppinsFontFamily(): FontFamily = FontFamily(
    // Agora usamos a referência segura do objeto Res, em vez de uma String
    Font(Res.font.poppins_regular, FontWeight.Normal),
    Font(Res.font.poppins_medium, FontWeight.Medium),
    Font(Res.font.poppins_bold, FontWeight.Bold)
)