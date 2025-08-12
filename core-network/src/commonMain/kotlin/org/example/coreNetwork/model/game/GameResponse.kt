package org.example.coreNetwork.model.game
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameResponse(
    @SerialName("results")
    val result: List<Result>,
)