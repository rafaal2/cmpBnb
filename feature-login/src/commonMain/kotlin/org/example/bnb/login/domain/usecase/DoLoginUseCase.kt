package org.example.bnb.login.domain.usecase

import org.example.bnb.login.domain.model.UserSession
import org.example.bnb.login.domain.repository.LoginRepository

/**
 * O caso de uso para executar a lógica de login.
 * Ele orquestra a chamada para o repositório.
 * A anotação 'operator fun invoke' permite chamar a classe como se fosse uma função.
 */
class DoLoginUseCase(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(email: String, password: String): Result<UserSession> {
        // Validação da Regra de Negócio
//        if (password.length < 8) {
//            return Result.failure(Exception("A senha deve ter no mínimo 8 caracteres."))
//        }
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(Exception("E-mail e senha não podem ser vazios."))
        }
        if (email.contains("\n") || password.contains("\n")) {
            return Result.failure(Exception("Campos não podem conter quebras de linha."))
        }

        // Se tudo estiver válido, prossiga para a chamada de rede
        return loginRepository.login(email, password)
    }
}