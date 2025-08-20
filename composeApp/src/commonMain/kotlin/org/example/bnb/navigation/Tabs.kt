package org.example.bnb.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import org.example.bnb.core.navigation.AppRoute
import org.example.bnb.discover.ui.DiscoverScreen
import org.example.bnb.listingdetails.ui.ListingDetailsScreen

internal object DiscoverTab : Tab {
    override val options: TabOptions
        @Composable get() {
            val icon = rememberVectorPainter(Icons.Default.Search)
            return androidx.compose.runtime.remember {
                TabOptions(index = 0u, title = "Descobrir", icon = icon)
            }
        }

    @Composable
    override fun Content() {
        val rootNavigator = rememberRootNavigator()
        val appNavigator  = remember(rootNavigator) { AppNavigator(rootNavigator) }

        // Discover NUNCA conhece Screens concretos.
        DiscoverScreen(
            onNavigate = appNavigator::open
        ).Content()
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
        ComingSoonScreen(tabName = "Perfil")
    }
}

@Composable
private fun ComingSoonScreen(tabName: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Tela de $tabName em breve...")
    }
}

