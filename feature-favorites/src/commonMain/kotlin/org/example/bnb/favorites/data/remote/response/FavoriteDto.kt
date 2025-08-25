package org.example.bnb.favorites.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoriteDto(
    val id: Int,
    val name: String,
    @SerialName("background_image")
    val backgroundImage: String?,
    val rating: Double,
    val price: Double
)

@Serializable
data class FavoritesResponseDto(
    val results: List<FavoriteDto>
)