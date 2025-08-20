package org.example.bnb.discover.domain.model

data class Listing(
    val id: String,
    val name: String,
    val imageUrl: String,
    val rating: Double,
    val price: Double
)
