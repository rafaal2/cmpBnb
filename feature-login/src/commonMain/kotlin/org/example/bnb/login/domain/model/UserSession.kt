package org.example.bnb.login.domain.model

/**
 * Representa uma sessão de usuário autenticada no aplicativo.
 * É um modelo de negócio puro, sem detalhes de API.
 */
data class UserSession(
    val userId: String
)