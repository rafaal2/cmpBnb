package org.example.bnb.favorites.data.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ToggleFavoriteRequestDto(
    val action: String, // Será "add" ou "delete"
    @SerialName("user_id")
    val userId: Int,
    @SerialName("acomodacao_id")
    val acomodacaoId: Int
)