package org.example.bnb

import androidx.compose.runtime.*
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.SlideTransition
import org.example.bnb.ui.theme.BnbTheme
import org.example.bnb.login.ui.LoginScreen

/**
 * Define os pontos de entrada principais da navegação do app.
 * Cada objeto implementa a interface 'Screen' da Voyager.
 */
sealed class AppScreen : Screen {
    @Composable
    override fun Content() {
        // O conteúdo real é definido em cada objeto filho.
    }

    data object Login : AppScreen() {
        @Composable
        override fun Content() {
            val navigator = LocalNavigator.currentOrThrow
            LoginScreen(
                onLoginSuccess = {
                    // Substitui a tela de login pela tela principal.
                    // O usuário não poderá voltar para o login.
                    navigator.replaceAll(Main)
                }
            )
        }
    }

    data object Main : AppScreen() {
        @Composable
        override fun Content() {
            // Chama o Composable da tela principal, que contém as abas.
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
        // O Navigator da Voyager gerencia toda a pilha de navegação.
        Navigator(screen = startScreen) { navigator ->
            // Aplica uma animação de transição a todas as telas.
            SlideTransition(navigator)
        }
    }
}