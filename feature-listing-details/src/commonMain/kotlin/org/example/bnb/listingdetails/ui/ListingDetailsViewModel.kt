package org.example.bnb.listingdetails.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.bnb.listingdetails.domain.usecase.GetListingDetailsUseCase

class ListingDetailsViewModel(
    private val listingId: String, // Recebe o ID da acomodação
    private val getListingDetailsUseCase: GetListingDetailsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ListingDetailsState>(ListingDetailsState.Loading)
    val state = _state.asStateFlow()

    init {
        loadDetails()
    }

    private fun loadDetails() {
        viewModelScope.launch {
            _state.value = ListingDetailsState.Loading

            getListingDetailsUseCase(listingId)
                .onSuccess { details ->
                    _state.value = ListingDetailsState.Success(details)
                }
                .onFailure { error ->
                    _state.value = ListingDetailsState.Error(error.message ?: "Erro desconhecido")
                }
        }
    }
}