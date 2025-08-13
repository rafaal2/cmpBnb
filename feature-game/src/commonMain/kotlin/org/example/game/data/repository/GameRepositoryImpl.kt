package org.example.game.data.repository

import org.example.coreNetwork.apiService.ApiService
import org.example.game.data.mappers.toDomainListOfGames
import org.example.game.domain.model.Game
import org.example.game.domain.repository.GameRepository

class GameRepositoryImpl(private val apiService: ApiService) : GameRepository {

    // 👇 CORREÇÃO: Renomeie de 'GetGames' para 'getGames' (com 'g' minúsculo)
    override suspend fun getGames(): Result<List<Game>> {
        val result = apiService.getGames()
        return if (result.isSuccess) {
            // Supondo que a resposta da API tenha um campo 'results' ou similar
            Result.success(result.getOrThrow().result.toDomainListOfGames())
        } else {
            Result.failure(result.exceptionOrNull()!!)
        }
    }
}