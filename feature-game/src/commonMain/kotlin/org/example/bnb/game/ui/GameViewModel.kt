package org.example.bnb.game.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.bnb.game.domain.usecase.GetGamesUseCase

class GameViewModel(
    private val getGamesUseCase: GetGamesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(GameState())
    val state = _state.asStateFlow()

    init {
        onEvent(GameEvent.LoadGames)
    }

    private fun onEvent(event: GameEvent) {
        when (event) {
            GameEvent.LoadGames -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true, error = null) }
                    getGamesUseCase()
                        .onSuccess { games ->
                            _state.update { it.copy(isLoading = false, games = games) }
                        }
                        .onFailure { error ->
                            _state.update { it.copy(isLoading = false, error = error.message) }
                        }
                }
            }
        }
    }
}