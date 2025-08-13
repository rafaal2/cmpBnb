package org.example.bnb.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.bnb.login.domain.usecase.DoLoginUseCase

class LoginViewModel(
    private val doLoginUseCase: DoLoginUseCase
) : ViewModel() { // Herda da nossa ViewModel KMP

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChange -> {
                _state.update { it.copy(email = event.email) }
            }
            is LoginEvent.OnPasswordChange -> {
                _state.update { it.copy(password = event.password) }
            }
            LoginEvent.OnLoginClick -> {
                performLogin()
            }
            LoginEvent.DismissError -> {
                _state.update { it.copy(error = null) }
            }
        }
    }

    private fun performLogin() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val currentState = _state.value
            doLoginUseCase(currentState.email, currentState.password)
                .onSuccess { userSession ->
                    _state.update {
                        it.copy(isLoading = false, loginSuccess = true)
                    }
                    // Aqui você poderia, por exemplo, salvar a sessão do usuário
                    // e navegar para a próxima tela.
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(isLoading = false, error = error.message)
                    }
                }
        }
    }
}