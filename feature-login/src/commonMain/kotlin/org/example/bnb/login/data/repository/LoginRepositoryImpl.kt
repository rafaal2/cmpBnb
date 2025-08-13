package org.example.bnb.login.data.repository

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.example.bnb.login.data.mapper.toDomainModel
import org.example.bnb.login.data.remote.request.LoginRequestDto
import org.example.bnb.login.data.remote.response.LoginResponseDto
import org.example.bnb.login.domain.model.UserSession
import org.example.bnb.login.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val httpClient: HttpClient // Recebe o cliente Ktor do :core-network
) : LoginRepository {

    override suspend fun login(email: String, password: String): Result<UserSession> {
        // 'runCatching' captura automaticamente qualquer exceção (de rede, do mapper, etc.)
        // e a transforma em um Result.failure, tornando o código muito mais seguro.
        return runCatching {
            val requestDto = LoginRequestDto(login = email, senha = password)

            // Faz a chamada POST para o seu script PHP
            val responseDto = httpClient.post("http://localhost/testeSession/api/login.php") {
                contentType(ContentType.Application.Json)
                setBody(requestDto)
            }.body<LoginResponseDto>()

            // Manda a resposta para o "tradutor" (Mapper) e retorna o resultado
            responseDto.toDomainModel()
        }
    }
}