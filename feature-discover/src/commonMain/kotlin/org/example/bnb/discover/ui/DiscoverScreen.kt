package org.example.bnb.discover.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.example.bnb.core.navigation.AppRoute // 👈 1. IMPORTA APENAS O CONTRATO
import org.koin.compose.viewmodel.koinViewModel

/**
 * A Screen da Voyager que age como ponto de entrada.
 * A única responsabilidade dela é receber o callback 'onNavigate' e conectar a UI ao ViewModel.
 */
class DiscoverScreen(
    private val onNavigate: (AppRoute) -> Unit // 👈 2. RECEBE O "MENSAGEIRO" DE NAVEGAÇÃO
) : Screen {

    @Composable
    override fun Content() {
        val viewModel: DiscoverViewModel = koinViewModel()
        val state by viewModel.state.collectAsState()


        DiscoverScreenContent(
            state = state,
            onListingClick = { listingId ->
                // 👇 3. USA O CONTRATO PARA PEDIR A NAVEGAÇÃO
                onNavigate(AppRoute.ListingDetails(listingId = listingId))
            },
            onRetryClick = {
                viewModel.onEvent(DiscoverEvent.LoadListings)
            }
        )
    }
}

@Composable
private fun DiscoverScreenContent(
    modifier: Modifier = Modifier,
    state: DiscoverState,
    onListingClick: (listingId: String) -> Unit,
    onRetryClick: () -> Unit
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
                message = "Erro ao carregar acomodações.\n${state.error}",
                actionButton = {
                    Button(onClick = onRetryClick) {
                        Text("Tentar Novamente")
                    }
                }
            )
        } else if (state.listings.isEmpty()) {
            InfoMessage(
                message = "Nenhuma acomodação encontrada.",
                actionButton = {
                    Button(onClick = onRetryClick) {
                        Text("Recarregar")
                    }
                }
            )
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                // Título da seção
                Text(
                    text = "Acomodações populares",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 160.dp),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(state.listings, key = { it.id }) { listing ->
                        ListingItem( // Supondo que ListingItem exista
                            listing = listing,
                            onClick = { onListingClick(listing.id) }
                        )
                    }
                }
            }
        }
    }}

// O InfoMessage com o botão opcional
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
