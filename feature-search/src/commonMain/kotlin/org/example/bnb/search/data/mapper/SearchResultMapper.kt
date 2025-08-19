package org.example.bnb.search.data.mapper

import org.example.bnb.search.data.remote.response.SearchResultDto
import org.example.bnb.search.domain.model.SearchResult

fun SearchResultDto.toDomainModel(): SearchResult {
    return SearchResult(
        id = this.id.toString(),
        title = this.name,
        subtitle = "R$ ${this.price} / noite", // Podemos usar o preço como subtítulo
        imageUrl = this.backgroundImage ?: ""
    )
}