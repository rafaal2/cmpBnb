package org.example.bnb.game.data.repository

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
// 👇 Garanta que o mapper correto está importado
import org.example.bnb.game.data.mapper.toDomainModel
import org.example.bnb.game.data.remote.response.GameResponse
import org.example.bnb.game.domain.model.Game
import org.example.bnb.game.domain.repository.GameRepository

class GameRepositoryImpl(
    private val httpClient: HttpClient
) : GameRepository {

    private val BASE_URL = "https://api.rawg.io/api"
    private val API_KEY = "8d716de8bd4f47439bf3526458e6c34b"

    override suspend fun getGames(): Result<List<Game>> {
        return runCatching {
            Logger.d("GameRepository") { "Buscando jogos da API..." }

            val response = httpClient.get("$BASE_URL/games") {
                parameter("key", API_KEY)
            }.body<GameResponse>()

            // 👇 CORREÇÃO: Chame o mapper diretamente na lista, sem o .map extra
            response.results.map { it.toDomainModel() }

        }.onFailure { error ->
            Logger.e("GameRepository", error) { "Falha ao buscar jogos" }
        }
    }
}