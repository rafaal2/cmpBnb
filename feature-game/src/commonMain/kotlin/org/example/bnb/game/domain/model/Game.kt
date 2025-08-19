package org.example.bnb.game.domain.model

data class Game(
    val id: String,
    val name: String,
    val imageUrl: String,
    val rating: Double,
    val price: Double
)
