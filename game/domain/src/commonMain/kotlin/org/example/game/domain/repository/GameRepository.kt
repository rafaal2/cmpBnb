package org.example.game.domain.repository

import org.example.game.domain.model.Game

interface GameRepository {
    suspend fun GetGames() : Result<List<Game>>
}