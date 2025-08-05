package org.example.coreNetwork.model.game
import kotlinx.serialization.Serializable

@Serializable
data class GameResponse(
    val result: List<Result>,
)