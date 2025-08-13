package org.example.bnb.login.domain.usecase

import org.example.bnb.login.domain.repository.LoginRepository

/**
 * O caso de uso para executar a lógica de login.
 * Ele orquestra a chamada para o repositório.
 * A anotação 'operator fun invoke' permite chamar a classe como se fosse uma função.
 */
class DoLoginUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        loginRepository.login(email, password)
}