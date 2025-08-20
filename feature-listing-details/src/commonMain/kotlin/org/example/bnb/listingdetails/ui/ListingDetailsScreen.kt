package org.example.bnb.listingdetails.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import org.example.bnb.listingdetails.domain.model.Host
import org.example.bnb.listingdetails.domain.model.ListingDetails
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

// A Screen da Voyager continua a mesma
data class ListingDetailsScreen(val listingId: String) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: ListingDetailsViewModel = koinViewModel { parametersOf(listingId) }
        val state by viewModel.state.collectAsState()

        ListingDetailsScreenContent(
            state = state,
            onNavigateBack = { navigator.pop() },
            onFavoriteClick = { /* Lógica futura para favoritar */ },
            onReserveClick = { /* Lógica futura para reservar */ }
        )
    }
}

// O Composable de conteúdo, que gerencia os estados
@Composable
private fun ListingDetailsScreenContent(
    state: ListingDetailsState,
    onNavigateBack: () -> Unit,
    onFavoriteClick: () -> Unit,
    onReserveClick: () -> Unit
) {
    // O Scaffold agora só lida com a barra inferior de reserva
    Scaffold(
        bottomBar = {
            // Só mostra a barra de reserva se os dados foram carregados com sucesso
            if (state is ListingDetailsState.Success) {
                ReserveBottomBar(
                    price = state.details.price,
                    onReserveClick = onReserveClick
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is ListingDetailsState.Loading -> CircularProgressIndicator()
                is ListingDetailsState.Error -> Text(text = state.message)
                is ListingDetailsState.Success -> {
                    // O `TopAppBar` agora vive dentro do conteúdo para ficar por cima da imagem
                    DetailsContent(
                        details = state.details,
                        onNavigateBack = onNavigateBack,
                        onFavoriteClick = onFavoriteClick
                    )
                }
            }
        }
    }
}

// O conteúdo principal da tela
@Composable
private fun DetailsContent(
    details: ListingDetails,
    onNavigateBack: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Galeria de Fotos
            PhotoGallery(photos = details.photos)

            // Corpo com as informações
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                HeaderSection(details = details)
                Divider()
                HostSection(host = details.host)
                Divider()
                DescriptionSection(description = details.description)
                // Aqui você adicionaria as seções de comodidades e reviews no futuro
            }
        }

        // TopAppBar flutuante com botões de voltar e favoritar
        DetailsTopAppBar(
            onNavigateBack = onNavigateBack,
            onFavoriteClick = onFavoriteClick
        )
    }
}

// --- Componentes Reutilizáveis da Tela de Detalhes ---

@Composable
private fun DetailsTopAppBar(
    onNavigateBack: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    var isFavorited by remember { mutableStateOf(false) } // Estado fake

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = onNavigateBack,
            modifier = Modifier.background(Color.White.copy(alpha = 0.7f), CircleShape)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
        }
        IconButton(
            onClick = {
                isFavorited = !isFavorited
                onFavoriteClick()
            },
            modifier = Modifier.background(Color.White.copy(alpha = 0.7f), CircleShape)
        ) {
            Icon(
                imageVector = if (isFavorited) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Favoritar",
                tint = if (isFavorited) Color.Red else Color.Black
            )
        }
    }
}

@Composable
private fun PhotoGallery(photos: List<String>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth().height(250.dp),
    ) {
        items(photos) { photoUrl ->
            AsyncImage(
                model = photoUrl,
                contentDescription = "Foto da acomodação",
                modifier = Modifier.fillParentMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
private fun HeaderSection(details: ListingDetails) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = details.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Star, contentDescription = "Avaliação", tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${details.rating}  ·  ${details.reviewCount} avaliações  ·  ${details.location.address}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Text(
            text = "${details.maxGuests} hóspedes · ${details.bedrooms} quarto(s) · ${details.beds} cama(s) · ${details.bathrooms} banheiro(s)",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun HostSection(host: Host) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text(text = "Anfitrião: ${host.name}", style = MaterialTheme.typography.titleLarge)
            if (host.isSuperhost) {
                Text("Superhost", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            }
        }
        AsyncImage(
            model = host.avatarUrl,
            contentDescription = "Foto do anfitrião",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )
    }
}

@Composable
private fun DescriptionSection(description: String) {
    var isExpanded by remember { mutableStateOf(false) }
    val maxLines = if (isExpanded) Int.MAX_VALUE else 4

    Column {
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis
        )
        TextButton(onClick = { isExpanded = !isExpanded }) {
            Text(if (isExpanded) "Mostrar menos" else "Mostrar mais")
        }
    }
}

@Composable
private fun ReserveBottomBar(price: Double, onReserveClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 8.dp // Sombra para destacar a barra
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("R$ $price", fontWeight = FontWeight.Bold)
                Text("noite")
            }
            Button(onClick = onReserveClick) {
                Text("Reservar")
            }
        }
    }
}