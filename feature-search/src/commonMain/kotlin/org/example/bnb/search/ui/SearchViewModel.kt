package org.example.bnb.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.bnb.search.domain.usecase.PerformSearchUseCase

class SearchViewModel(
    private val performSearchUseCase: PerformSearchUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    private var searchJob: Job? = null

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnQueryChange -> {
                _state.update { it.copy(searchQuery = event.query) }
                // Debounce: espera 500ms após o usuário parar de digitar para fazer a busca
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    performSearch()
                }
            }
        }
    }

    private fun performSearch() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            performSearchUseCase(state.value.searchQuery)
                .onSuccess { results ->
                    _state.update { it.copy(isLoading = false, searchResults = results) }
                }
                .onFailure { error ->
                    _state.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }
}