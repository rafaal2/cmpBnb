package org.example.bnb.discover.ui // Ou o pacote da sua nova feature

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.crossfade
import org.example.bnb.discover.domain.model.Listing

@Composable
fun ListingItem(
    listing: Listing, // Supondo que 'Game' agora tenha 'rating' e 'price'
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .aspectRatio(1f), // <-- MUDANÇA: Proporção 1 para 1 (quadrado)
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().clickable(onClick = onClick)
        ) {
            // Imagem de fundo
            AsyncImage(
                model = coil3.request.ImageRequest.Builder(LocalPlatformContext.current) // veja nota abaixo
                    .data(listing.imageUrl)
                    .crossfade(true)            // 👈 fade suave
                    .build(),
                contentDescription = listing.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
                error = ColorPainter(MaterialTheme.colorScheme.surfaceVariant)
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter) // 1. Alinha o Box na base do contêiner pai
                    .fillMaxWidth()               // 2. Faz o Box ocupar toda a largura
                    .fillMaxHeight(0.4f)          // 3. Faz o Box ocupar 40% da altura (ajuste conforme o gosto)
                    .background(
                        Brush.verticalGradient(
                            // 4. O gradiente agora vai de preto (embaixo) para transparente (em cima)
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                        )
                    )
            )

            // --- NOVO: Avaliação (Rating) no topo ---
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Avaliação",
                    tint = Color.Yellow,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = listing.rating.toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                )
            }

            // --- NOVO: Coluna para Nome e Preço na base ---
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            ) {
                Text(
                    text = listing.name,
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "R$ ${listing.price} / noite",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}