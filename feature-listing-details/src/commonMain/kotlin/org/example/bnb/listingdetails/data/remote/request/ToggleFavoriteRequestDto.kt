package org.example.bnb.listingdetails.data.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// DTO duplicado, mas que garante a independência do módulo
@Serializable
data class ToggleFavoriteRequestDto(
    val action: String, // "add" ou "delete"
    @SerialName("user_id")
    val userId: Int,
    @SerialName("acomodacao_id")
    val acomodacaoId: Int
)