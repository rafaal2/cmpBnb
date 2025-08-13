package org.example.bnb.game.domain.usecase

import org.example.bnb.game.domain.repository.GameRepository

class GetGamesUseCase(
    private val repository: GameRepository
) {
    // O ViewModel chamará este UseCase, não o repositório diretamente.
    suspend operator fun invoke() = repository.getGames()
}