package org.example.game.ui

import org.example.game.domain.model.Game

// Define o estado e os eventos da tela para um padrão MVI
data class GameState(
    val isLoading: Boolean = false,
    val games: List<Game> = emptyList(),
    val error: String? = null
)

sealed interface GameEvent {
    object LoadGames : GameEvent
}