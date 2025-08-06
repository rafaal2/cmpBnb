package org.example.game.data.repository

import org.example.coreNetwork.apiService.ApiService
import org.example.game.data.mappers.toDomainListOfGames
import org.example.game.domain.model.Game
import org.example.game.domain.repository.GameRepository

class GameRepositoryImpl(private val apiService: ApiService) : GameRepository {
    override suspend fun GetGames(): Result<List<Game>> {
        val result = apiService.getGames()
        return if (result.isSuccess) {
            Result.success(result.getOrThrow().result.toDomainListOfGames())
        } else {
            Result.failure(result.exceptionOrNull()!!)
        }
    }
}