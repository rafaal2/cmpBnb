package org.example.bnb.game.domain.repository

import org.example.bnb.game.domain.model.Game

interface GameRepository {
    suspend fun getGames() : Result<List<Game>>
}