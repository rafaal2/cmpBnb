package org.example.bnb.splash

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.bnb.core.ui.ViewModel
import org.example.bnb.login.domain.usecase.CheckSessionUseCase

sealed interface SplashUiState {
    object Loading : SplashUiState
    object Authenticated : SplashUiState
    object Unauthenticated : SplashUiState
}

class SplashViewModel(checkSessionUseCase: CheckSessionUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        // Assim que o ViewModel é criado, ele verifica a sessão
        if (checkSessionUseCase()) {
            _uiState.value = SplashUiState.Authenticated
        } else {
            _uiState.value = SplashUiState.Unauthenticated
        }
    }
}