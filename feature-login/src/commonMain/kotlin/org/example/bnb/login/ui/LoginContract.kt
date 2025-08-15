package org.example.bnb.login.ui

/**
 * Define o "contrato" da tela de login: o estado e os eventos.
 */

// Representa tudo que a tela pode exibir.
data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null, // Erro vindo da API
    val emailError: String? = null, // Erro de validação do campo de e-mail
    val passwordError: String? = null, // Erro de validação do campo de senha
    val isLoginButtonEnabled: Boolean = false,
    val loginSuccess: Boolean = false
)

// Representa todas as ações que o usuário pode fazer.
sealed interface LoginEvent {
    data class OnEmailChange(val email: String) : LoginEvent
    data class OnPasswordChange(val password: String) : LoginEvent
    data object OnLoginClick : LoginEvent
    data object DismissError : LoginEvent
}