package org.example.coreNetwork.apiService

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.parameters
import org.example.coreNetwork.model.game.GameResponse

class ApiService(val httpClient: HttpClient) {
    suspend fun getGames(): Result<GameResponse> {
        return try {
            val response = httpClient.get("api/games") {
                url {
                    parameter("key", "8d716de8bd4f47439bf3526458e6c34b")
                }
            }.body<GameResponse>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}