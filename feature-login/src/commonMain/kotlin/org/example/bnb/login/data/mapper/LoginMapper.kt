package org.example.bnb.login.data.mapper

import org.example.bnb.login.data.remote.response.LoginResponseDto
import org.example.bnb.login.domain.model.UserSession

/**
 * Converte o DTO da camada de dados para o Modelo da camada de domínio.
 * Esta função centraliza a lógica de validação da resposta da API.
 */
fun LoginResponseDto.toDomainModel(): UserSession {
    // Se a API disse que o login foi um sucesso e nos deu um ID,
    // nós criamos e retornamos o nosso objeto de negócio 'UserSession'.
    if (this.success && this.id != null) {
        return UserSession(
            userId = this.id.toString()
        )
    } else {
        // Se a API disse que falhou, ou não retornou um ID,
        // nós lançamos uma exceção. O Repository vai capturar isso.
        throw Exception(this.error ?: "API retornou sucesso, mas sem ID de usuário.")
    }
}