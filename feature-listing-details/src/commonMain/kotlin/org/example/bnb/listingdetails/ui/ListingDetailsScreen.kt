package org.example.bnb.listingdetails.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import org.example.bnb.listingdetails.domain.model.ListingDetails
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

// A tela da Voyager agora precisa saber o ID
data class ListingDetailsScreen(val listingId: String) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        // Passamos o ID para o Koin para que ele possa criar o ViewModel
        val viewModel: ListingDetailsViewModel = koinViewModel { parametersOf(listingId) }
        val state by viewModel.state.collectAsState()

        ListingDetailsScreenContent(
            state = state,
            onNavigateBack = { navigator.pop() }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListingDetailsScreenContent(
    state: ListingDetailsState,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is ListingDetailsState.Loading -> {
                    CircularProgressIndicator()
                }
                is ListingDetailsState.Error -> {
                    Text(text = state.message)
                }
                is ListingDetailsState.Success -> {
                    // Passamos os detalhes para um Composable que sabe como exibi-los
                    DetailsContent(details = state.details)
                }
            }
        }
    }
}

// Composable que exibe os detalhes de forma simples
@Composable
private fun DetailsContent(details: ListingDetails) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Galeria de fotos simples
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(details.photos) { photoUrl ->
                AsyncImage(
                    model = photoUrl,
                    contentDescription = "Foto da acomodação",
                    modifier = Modifier.height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Informações principais
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(text = details.name, style = MaterialTheme.typography.headlineMedium)
            Text(
                text = "★ ${details.rating}  ·  ${details.reviewCount} avaliações",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 4.dp)
            )
            Divider(modifier = Modifier.padding(vertical = 16.dp))
            Text(
                text = "Anfitrião: ${details.host.name}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = details.description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}