package org.example.bnb

import androidx.compose.runtime.*
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.SlideTransition
import org.example.bnb.ui.theme.BnbTheme
import org.example.bnb.login.ui.LoginScreen

sealed class AppScreen : Screen {
    @Composable
    override fun Content() {
    }

    data object Login : AppScreen() {
        @Composable
        override fun Content() {
            val navigator = LocalNavigator.currentOrThrow
            LoginScreen(
                onLoginSuccess = {
                    navigator.replaceAll(Main)
                }
            )
        }
    }

    data object Main : AppScreen() {
        @Composable
        override fun Content() {
            MainScreen.Content()
        }
    }
}

/**
 * O Composable raiz do aplicativo.
 * Sua única responsabilidade é criar o Tema e o Navegador principal.
 */
@Composable
fun App(startScreen: AppScreen) {
    BnbTheme {
        Navigator(screen = startScreen) { navigator ->
            SlideTransition(navigator)
        }
    }
}