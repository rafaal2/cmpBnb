package org.example.bnb

import androidx.compose.runtime.*
import org.example.bnb.ui.theme.BnbTheme
import org.example.bnb.login.ui.LoginScreen

sealed class Screen {
    object Login : Screen()
    object Main : Screen() // <-- Mudamos de Game para Main
}

@Composable
fun App(startScreen: Screen) {
    BnbTheme {
        var currentScreen by remember { mutableStateOf(startScreen) }

        when (currentScreen) {
            is Screen.Login -> {
                LoginScreen(
                    onLoginSuccess = {
                        // Quando o login for bem-sucedido, vamos para a tela principal
                        currentScreen = Screen.Main
                    },
                    onCreateAccountClick = { /* Lógica para criar conta */ }
                )
            }
            // A GameScreen agora é gerenciada pelo TabNavigator dentro da MainScreen
            is Screen.Main -> {
                MainScreen()
            }
        }
    }
}