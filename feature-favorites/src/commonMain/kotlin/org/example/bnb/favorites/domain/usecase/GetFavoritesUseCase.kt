package org.example.bnb.favorites.domain.usecase

import org.example.bnb.favorites.domain.repository.FavoritesRepository

class GetFavoritesUseCase(
    private val favoritesRepository: FavoritesRepository
) {
    suspend operator fun invoke(userId: String) = favoritesRepository.getFavorites(userId)
}