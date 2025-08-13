package org.example.bnb.game.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class GameResponse(
    val results: List<GameDto>
)