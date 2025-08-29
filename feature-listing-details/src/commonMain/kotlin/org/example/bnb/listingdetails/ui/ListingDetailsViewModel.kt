package org.example.bnb.listingdetails.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.bnb.listingdetails.domain.usecase.AddFavoriteUseCase
import org.example.bnb.listingdetails.domain.usecase.GetListingDetailsUseCase
import org.example.bnb.listingdetails.domain.usecase.RemoveFavoriteUseCase


class ListingDetailsViewModel(
    private val listingId: String,
    private val getListingDetailsUseCase: GetListingDetailsUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ListingDetailsState>(ListingDetailsState.Loading)
    val state = _state.asStateFlow()

    init {
        loadDetails()
    }

    fun onEvent(event: ListingDetailsEvent) {
        when(event) {
            ListingDetailsEvent.ToggleFavorite -> toggleFavorite()
        }
    }

    private fun toggleFavorite() {
        viewModelScope.launch {
            val currentState = _state.value
            if (currentState is ListingDetailsState.Success) {
                val currentDetails = currentState.details
                val isCurrentlyFavorite = currentDetails.isFavorite

                // 1. Atualização Otimista: Mude a UI imediatamente
                val updatedDetails = currentDetails.copy(isFavorite = !isCurrentlyFavorite)
                _state.value = ListingDetailsState.Success(updatedDetails)

                // TODO: Substituir "1" pelo ID do usuário real
                val userId = "1"

                val result = if (isCurrentlyFavorite) {
                    removeFavoriteUseCase(userId, listingId)
                } else {
                    addFavoriteUseCase(userId, listingId)
                }

                // 2. Se a chamada de API falhar, reverta a UI para o estado anterior
                result.onFailure {
                    _state.value = currentState
                    // TODO: Mostrar um snackbar de erro
                }
            }
        }
    }

    private fun loadDetails() {
        viewModelScope.launch {
            _state.value = ListingDetailsState.Loading
            getListingDetailsUseCase(listingId)
                .onSuccess { details ->
                    // TODO: Você precisará que a API de detalhes informe se o item é favorito
                    // Por enquanto, vamos assumir que não é.
                    val detailsWithFavoriteStatus = details.copy(isFavorite = false)
                    _state.value = ListingDetailsState.Success(detailsWithFavoriteStatus)
                }
                .onFailure { error ->
                    _state.value = ListingDetailsState.Error(error.message ?: "Erro desconhecido")
                }
        }
    }
}