package org.example.bnb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import org.example.bnb.splash.SplashUiState
import org.example.bnb.splash.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel // 👈 1. MUDE A IMPORTAÇÃO

// 2. A Activity NÃO PRECISA mais implementar KoinComponent
class MainActivity : ComponentActivity() {

    // 3. TROQUE 'inject()' por 'viewModel()'
    private val splashViewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition {
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