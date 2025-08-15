package org.example.bnb

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity // 👈 1. MUDE A IMPORTAÇÃO E A HERANÇA
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import org.example.bnb.splash.SplashUiState
import org.example.bnb.splash.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() { // 👈 HERDE DE AppCompatActivity

    private val splashViewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        // 2. A CHAMADA DA SPLASH SCREEN DEVE VIR PRIMEIRO
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        // 3. O RESTO DA CONFIGURAÇÃO VEM DEPOIS
        WindowCompat.setDecorFitsSystemWindows(window, false)

        splashScreen.setKeepOnScreenCondition {
            splashViewModel.uiState.value == SplashUiState.Loading
        }

        setContent {
            val startScreen = when (splashViewModel.uiState.value) {
                is SplashUiState.Authenticated -> Screen.Game
                else -> Screen.Login
            }
            App(startScreen = startScreen)
        }
    }
}