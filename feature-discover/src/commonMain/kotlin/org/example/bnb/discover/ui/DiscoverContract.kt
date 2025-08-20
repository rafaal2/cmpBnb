package org.example.bnb.discover.ui // Pacote atualizado

import org.example.bnb.discover.domain.model.Listing // Importe o modelo de domínio correto

// O nome do State está bom. Apenas a propriedade interna precisa de ajuste.
data class DiscoverState(
    val isLoading: Boolean = false,
    val listings: List<Listing> = emptyList(), // <-- MUDANÇA: de 'games' para 'listings'
    val error: String? = null
)

// O Event precisa ser renomeado para refletir a nova feature.
sealed interface DiscoverEvent {
    object LoadListings : DiscoverEvent // <-- MUDANÇA: de 'GameEvent' para 'DiscoverEvent' e 'loadListings' para 'LoadListings'
}