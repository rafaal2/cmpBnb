package org.example.bnb.favorites.domain.usecase

import org.example.bnb.favorites.domain.repository.FavoritesRepository

class RemoveFavoriteUseCase(
    private val favoritesRepository: FavoritesRepository
) {
    suspend operator fun invoke(userId: String, listingId: String) =
        favoritesRepository.removeFavorite(userId, listingId)
}