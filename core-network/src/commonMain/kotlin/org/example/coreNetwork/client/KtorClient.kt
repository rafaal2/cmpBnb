package org.example.coreNetwork.client

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.ContentType.Message.Http
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorClient {
    fun getInstance(): HttpClient = HttpClient{
        install(ContentNegotiation){
            json(json = Json{
                ignoreUnknownKeys = true
            })
        }
//https://api.rawg.io/api/games?key=8d716de8bd4f47439bf3526458e6c34b
        install(DefaultRequest){
            url{
                host = "api.rawg.io"
                protocol = URLProtocol.HTTPS
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }
        }

        install(HttpTimeout){
            socketTimeoutMillis = 3000
            connectTimeoutMillis = 3000
            requestTimeoutMillis = 3000
        }
    }
}