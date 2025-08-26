package org.example.bnb.favorites.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                // 👇 3. USA O CONTRATO PARA PEDIR A NAVEGAÇÃO
                onNavigate(AppRoute.ListingDetails(listingId = listingId))
            },
            onRetryClick = {
                viewModel.onEvent(FavoritesEvent.LoadFavorites)
            }
        )
    }
}


@Composable
private fun FavoritesScreenContent(
    state: FavoritesState,
    onRemoveClick: (listingId: String) -> Unit,
    onListingClick: (listingId: String) -> Unit,
    onRetryClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.error != null) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Erro ao carregar favoritos.")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onRetryClick) {
                    Text("Tentar Novamente")
                }
            }
        } else if (state.favorites.isEmpty()) {
            Text(text = "Você ainda não tem acomodações favoritas.")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(state.favorites, key = { it.id }) { listing ->
                    // Aqui está a mágica da reutilização!
                    // Estamos usando o mesmo 'ListingItem' da feature 'discover'.
                    FavoriteItem(
                        listing = listing, // O tipo de dados precisa ser compatível
                        onClick = { onListingClick(listing.id) }
                        // Poderíamos adicionar um botão de "remover" aqui
                    )
                }
            }
        }
    }
}