package org.example.bnb.discover.ui // Pacote atualizado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.bnb.discover.domain.usecase.GetListingsUseCase

class DiscoverViewModel(
    private val getListingsUseCase: GetListingsUseCase // <-- MUDANÇA: UseCase correto
) : ViewModel() {

    private val _state = MutableStateFlow(DiscoverState()) // <-- MUDANÇA: State correto
    val state = _state.asStateFlow()

    // Lembre-se: a chamada inicial de dados será feita pela UI com LaunchedEffect,
    // então o 'init' aqui não é mais necessário. A UI terá o controle.

    fun onEvent(event: DiscoverEvent) { // <-- MUDANÇA: Event correto
        when (event) {
            DiscoverEvent.LoadListings -> {
                loadListings()
            }
        }
    }

    fun loadListings() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            getListingsUseCase()
                .onSuccess { listingsResult ->
                    _state.update { it.copy(isLoading = false, listings = listingsResult) }
                }
                .onFailure { error ->
                    _state.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }
}