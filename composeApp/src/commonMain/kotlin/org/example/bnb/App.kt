package org.example.bnb

import androidx.compose.runtime.*
import org.example.bnb.game.ui.GameScreen
import org.example.bnb.login.ui.LoginScreen
import org.example.bnb.ui.theme.BnbTheme

sealed class Screen {
    object Login : Screen()
    object Game : Screen()
}

@Composable
// 👇 1. ADICIONE O PARÂMETRO AQUI
fun App(startScreen: Screen) {
    BnbTheme {

        // 2. O ESTADO AGORA USA O PARÂMETRO RECEBIDO COMO VALOR INICIAL
        var currentScreen by remember { mutableStateOf(startScreen) }

        when (currentScreen) {
            is Screen.Login -> {
                LoginScreen(
                    onLoginSuccess = {
                        currentScreen = Screen.Game
                    }
                )
            }
            is Screen.Game -> {
                GameScreen()
            }
        }
    }
}