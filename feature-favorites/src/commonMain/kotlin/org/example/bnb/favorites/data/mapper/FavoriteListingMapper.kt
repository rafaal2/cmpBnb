package org.example.bnb.favorites.data.mapper

import org.example.bnb.favorites.data.remote.response.FavoriteDto
import org.example.bnb.favorites.domain.model.FavoriteListing

fun FavoriteDto.toDomainModel(): FavoriteListing {
    return FavoriteListing(
        id = this.id.toString(),
        name = this.name,
        imageUrl = this.backgroundImage ?: "",
        price = this.price,
        rating = this.rating
    )
}