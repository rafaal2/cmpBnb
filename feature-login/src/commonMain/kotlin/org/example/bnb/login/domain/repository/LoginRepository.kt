package org.example.bnb.login.domain.repository

import org.example.bnb.login.domain.model.UserSession

/**
 * A interface (contrato) que define as operações de dados para a feature de login.
 * O UseCase dependerá desta abstração, não da implementação concreta.
 */
interface LoginRepository {
    suspend fun login(email: String, password: String): Result<UserSession>
}