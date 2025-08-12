package org.example.game.domain.usecase

import org.example.game.domain.repository.GameRepository

class GetGamesUseCase(
    private val repository: GameRepository
) {
    // O ViewModel chamará este UseCase, não o repositório diretamente.
    suspend operator fun invoke() = repository.getGames()
}