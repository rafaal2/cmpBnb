package org.example.bnb.favorites.domain.model

data class FavoriteListing(
    val id: String,
    val name: String,
    val imageUrl: String,
    val price: Double,
    val rating: Double
)