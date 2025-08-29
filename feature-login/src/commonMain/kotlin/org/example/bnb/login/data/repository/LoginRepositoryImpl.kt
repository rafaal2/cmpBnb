package org.example.bnb.login.data.repository

import co.touchlab.kermit.Logger
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.expectSuccess
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json // 👈 IMPORTE o Json
import org.example.bnb.login.data.mapper.toDomainModel
import org.example.bnb.login.data.remote.request.LoginRequestDto
import org.example.bnb.login.data.remote.response.LoginResponseDto
import org.example.bnb.login.domain.model.UserSession
import org.example.bnb.login.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val httpClient: HttpClient
) : LoginRepository {

    override suspend fun login(email: String, password: String): Result<UserSession> {
        return runCatching {
            val requestDto = LoginRequestDto(login = email, senha = password)

            val response: HttpResponse = httpClient.post("http://gestorwebsaude.com.br/vaidehotel/app/api/login.php") {
                contentType(ContentType.Application.Json)
                setBody(requestDto)
                expectSuccess = false
            }

            // 1. Leia o corpo da resposta como uma String de texto.
            //    Isso funciona de forma confiável para sucesso e erro.
            val responseBodyAsText = response.bodyAsText()

            // 2. Tente decodificar (parse) a string de texto para o nosso DTO.
            val responseDto = Json.decodeFromString<LoginResponseDto>(responseBodyAsText)

            // 3. A lógica do mapper continua a mesma.
            //    Se a resposta for de falha (ex: success=false), o mapper vai
            //    lançar uma exceção, que será capturada pelo 'runCatching'.
            responseDto.toDomainModel()

        }.onFailure { error ->
            Logger.e("LoginRepository", error) { "Falha na chamada de login" }
        }
    }
}