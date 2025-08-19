package org.example.bnb

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import org.example.bnb.splash.SplashUiState
import org.example.bnb.splash.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        splashScreen.setKeepOnScreenCondition {
            splashViewModel.uiState.value == SplashUiState.Loading
        }

        setContent {
            // 👇 CORREÇÃO AQUI: Use o novo nome 'AppScreen'
            val startScreen = when (splashViewModel.uiState.value) {
                is SplashUiState.Authenticated -> AppScreen.Main
                else -> AppScreen.Login
            }
            App(startScreen = startScreen)
        }
    }
}