package org.example.bnb.favorites.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.bnb.favorites.domain.usecase.GetFavoritesUseCase
import org.example.bnb.favorites.domain.usecase.RemoveFavoriteUseCase

class FavoritesViewModel(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase
    // Supondo que você tenha um UseCase para pegar o ID do usuário logado
    // private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FavoritesState())
    val state = _state.asStateFlow()

    fun onEvent(event: FavoritesEvent) {
        when (event) {
            FavoritesEvent.LoadFavorites, FavoritesEvent.RefreshFavorites -> {
                loadFavorites()
            }
            is FavoritesEvent.RemoveFavorite -> {
                removeFavorite(event.listingId)
            }
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            // TODO: Substituir "1" pelo ID do usuário logado de verdade
            val userId = "1"

            getFavoritesUseCase(userId)
                .onSuccess { favoritesList ->
                    _state.update { it.copy(isLoading = false, favorites = favoritesList) }
                }
                .onFailure { error ->
                    _state.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }

    private fun removeFavorite(listingId: String) {
        viewModelScope.launch {
            // TODO: Substituir "1" pelo ID do usuário logado de verdade
            val userId = "1"

            removeFavoriteUseCase(userId, listingId)
                .onSuccess {
                    // Após remover, atualiza a lista para refletir a mudança
                    loadFavorites()
                }
                .onFailure { error ->
                    // Idealmente, mostrar um Snackbar de erro aqui
                    _state.update { it.copy(error = "Erro ao remover favorito: ${error.message}") }
                }
        }
    }
}