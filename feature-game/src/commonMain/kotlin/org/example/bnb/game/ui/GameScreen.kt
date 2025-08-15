package org.example.bnb.game.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    // Callback para navegar para a tela de detalhes quando um jogo for clicado
    onGameClick: (gameId: String) -> Unit = {},
    viewModel: GameViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Games Populares") }) }
    ) { paddingValues ->
        GameScreenContent(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onGameClick = onGameClick
        )
    }
}

@Composable
private fun GameScreenContent(
    modifier: Modifier = Modifier,
    state: GameState,
    onGameClick: (gameId: String) -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.error != null) {
            InfoMessage(
                icon = Icons.Default.Warning,
                message = "Erro ao carregar os jogos.\n${state.error}"
            )
        } else if (state.games.isEmpty()) {
            InfoMessage(message = "Nenhum jogo encontrado.")
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 160.dp), // Grade adaptativa
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp) // Espaçamento nas bordas da grade
            ) {
                items(state.games, key = { it.id }) { game ->
                    GameItem(
                        game = game,
                        onClick = { onGameClick(game.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun InfoMessage(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    message: String
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun GameScreenPreview_Loading() {
//    BnbTheme {
//        GameScreenContent(state = GameState(isLoading = true), onGameClick = {})
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun GameScreenPreview_Content() {
//    BnbTheme {
//        val sampleGames = listOf(
//            Game("1", "Cyberpunk 2077", "", 4.8, false),
//            Game("2", "The Witcher 3", "", 4.9, true),
//        )
//        GameScreenContent(state = GameState(games = sampleGames), onGameClick = {})
//    }
//}