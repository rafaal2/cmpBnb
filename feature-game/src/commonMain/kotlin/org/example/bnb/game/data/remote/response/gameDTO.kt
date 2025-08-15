package org.example.bnb.game.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameDto(
    val id: Int,
    val name: String,
    @SerialName("background_image")
    val backgroundImage: String?,
    val rating: Double
)