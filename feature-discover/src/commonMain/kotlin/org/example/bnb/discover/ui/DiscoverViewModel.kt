package org.example.bnb.discover.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.bnb.discover.domain.usecase.GetListingsUseCase

class DiscoverViewModel(
    private val getListingsUseCase: GetListingsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DiscoverState())
    val state = _state.asStateFlow()

    init {
        loadListings()
    }

    fun onEvent(event: DiscoverEvent) {
        when (event) {
            DiscoverEvent.LoadListings -> loadListings()
        }
    }

    private fun loadListings() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            getListingsUseCase()
                .onSuccess { listings ->
                    _state.update { it.copy(isLoading = false, listings = listings) }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
        }
    }
}