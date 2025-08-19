package org.example.bnb.game.data.repository

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.example.bnb.game.data.mapper.toDomainModel
import org.example.bnb.game.data.remote.response.GameResponse
import org.example.bnb.game.domain.model.Game
import org.example.bnb.game.domain.repository.GameRepository

class GameRepositoryImpl(
    private val httpClient: HttpClient
) : GameRepository {

    // 👇 MUDANÇA: A URL agora aponta para o seu servidor PHP local
    private val GET_ALL_URL = "http://10.0.2.2/testeSession/api/get_all_acomodacoes.php"

    override suspend fun getGames(): Result<List<Game>> {
        return runCatching {
            Logger.d("GameRepository") { "Buscando acomodações da API local..." }

            // 👇 A chamada agora usa a nova URL, sem API Key
            val response = httpClient.get(GET_ALL_URL).body<GameResponse>()

            // A lógica de mapeamento continua a mesma
            response.results.map { it.toDomainModel() }

        }.onFailure { error ->
            Logger.e("GameRepository", error) { "Falha ao buscar acomodações" }
        }
    }
}