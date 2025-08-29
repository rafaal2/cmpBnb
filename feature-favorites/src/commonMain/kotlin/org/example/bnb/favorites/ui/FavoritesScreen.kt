package org.example.bnb.favorites.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.example.bnb.core.navigation.AppRoute
import org.koin.compose.viewmodel.koinViewModel

class FavoritesScreen(
    private val onNavigate: (AppRoute) -> Unit // 👈 2. RECEBE O "MENSAGEIRO" DE NAVEGAÇÃO
) : Screen {
    @Composable
    override fun Content() {
        val viewModel: FavoritesViewModel = koinViewModel()
        val state by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.onEvent(FavoritesEvent.LoadFavorites)
        }

        FavoritesScreenContent(
            state = state,
            onRemoveClick = { listingId ->
                viewModel.onEvent(FavoritesEvent.RemoveFavorite(listingId))
            },
            onListingClick = { listingId ->
                onNavigate(AppRoute.ListingDetails(listingId = listingId))
            },
            onRetryClick = {
                viewModel.onEvent(FavoritesEvent.LoadFavorites)
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavoritesScreenContent(
    state: FavoritesState,
    onRemoveClick: (listingId: String) -> Unit,
    onListingClick: (listingId: String) -> Unit,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Favoritos",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else if (state.error != null) {
                InfoMessage(
                    icon = Icons.Default.Warning,
                    message = "Erro ao carregar favoritos.",
                    actionButton = { Button(onClick = onRetryClick) { Text("Tentar Novamente") } }
                )
            } else if (state.favorites.isEmpty()) {
                InfoMessage(
                    icon = Icons.Default.Favorite,
                    message = "Sua lista de favoritos está vazia.\nToque no coração para adicionar acomodações aqui."
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.favorites, key = { it.id }) { listing ->
                        FavoriteItem(
                            listing = listing,
                            onClick = { onListingClick(listing.id) },
                            onRemoveClick = { onRemoveClick(listing.id) }
                        )
                    }
                }
            }
        }
    }
}

// O InfoMessage pode ser reutilizado de outra feature ou definido aqui
@Composable
private fun InfoMessage(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    message: String,
    actionButton: (@Composable () -> Unit)? = null
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
        if (actionButton != null) {
            Spacer(modifier = Modifier.height(16.dp))
            actionButton()
        }
    }
}
