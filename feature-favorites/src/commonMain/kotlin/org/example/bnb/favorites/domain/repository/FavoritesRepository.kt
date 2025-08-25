package org.example.bnb.favorites.domain.repository

import org.example.bnb.favorites.domain.model.FavoriteListing

interface FavoritesRepository {
    suspend fun getFavorites(userId: String): Result<List<FavoriteListing>>
    suspend fun addFavorite(userId: String, listingId: String): Result<Unit>
    suspend fun removeFavorite(userId: String, listingId: String): Result<Unit>
}