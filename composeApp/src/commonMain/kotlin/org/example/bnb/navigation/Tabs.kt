package org.example.bnb.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.example.bnb.game.ui.GameScreen
// Importe as outras telas quando existirem
// import org.example.bnb.favorites.ui.FavoritesScreen
// import org.example.bnb.profile.ui.ProfileScreen

internal object GameTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Search)
            return remember { TabOptions(index = 0u, title = "Pesquisa", icon = icon) }
        }

    @Composable
    override fun Content() {
        // Aqui chamamos a tela da nossa feature!
        GameScreen(
            onGameClick = { gameId ->
                // No futuro, a navegação para os detalhes do jogo aconteceria aqui
                println("Game clicked: $gameId")
            }
        )
    }
}

internal object FavoritesTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Favorite)
            return remember { TabOptions(index = 1u, title = "Favoritos", icon = icon) }
        }

    @Composable
    override fun Content() {
        // Substitua por FavoritesScreen() quando ela existir
        // FavoritesScreen()
        ComingSoonScreen(tabName = "Favoritos")
    }
}

internal object ReserveTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.House)
            return remember { TabOptions(index = 2u, title = "Reservas", icon = icon) }
        }

    @Composable
    override fun Content() {
        // Substitua por ProfileScreen() quando ela existir
        // ProfileScreen()
        ComingSoonScreen(tabName = "Reservas")
    }
}
internal object ProfileTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Person)
            return remember { TabOptions(index = 3u, title = "Perfil", icon = icon) }
        }

    @Composable
    override fun Content() {
        // Substitua por ProfileScreen() quando ela existir
        // ProfileScreen()
        ComingSoonScreen(tabName = "Perfil")
    }
}

// Composable temporário para as telas que ainda não existem
@Composable
private fun ComingSoonScreen(tabName: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Tela de $tabName em breve...")
    }
}