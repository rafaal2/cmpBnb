package org.example.bnb.search.domain.model

// Este é o objeto que a UI vai usar para exibir os resultados.
data class SearchResult(
    val id: String,
    val title: String,
    val subtitle: String,
    val imageUrl: String
)