package org.example.bnb.favorites.ui

import org.example.bnb.favorites.domain.model.FavoriteListing

data class FavoritesState(
    val isLoading: Boolean = false,
    val favorites: List<FavoriteListing> = emptyList(),
    val error: String? = null
)

sealed interface FavoritesEvent {
    object LoadFavorites : FavoritesEvent
    // Opcional: para quando a lista for "arrastada para atualizar"
    object RefreshFavorites : FavoritesEvent
    data class RemoveFavorite(val listingId: String) : FavoritesEvent
}