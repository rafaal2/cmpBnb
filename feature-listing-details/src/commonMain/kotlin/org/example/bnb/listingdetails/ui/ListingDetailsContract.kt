package org.example.bnb.listingdetails.ui

import org.example.bnb.listingdetails.domain.model.ListingDetails

// Representa o que a tela pode exibir
sealed interface ListingDetailsState {
    object Loading : ListingDetailsState
    data class Error(val message: String) : ListingDetailsState
    data class Success(val details: ListingDetails) : ListingDetailsState
}

// Representa as ações que a UI pode iniciar (por enquanto, nenhuma)
sealed interface ListingDetailsEvent {
    // Ex: object OnFavoriteClick : ListingDetailsEvent
}