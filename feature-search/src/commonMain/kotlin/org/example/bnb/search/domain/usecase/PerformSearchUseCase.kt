package org.example.bnb.search.domain.usecase

import org.example.bnb.search.domain.model.SearchResult
import org.example.bnb.search.domain.repository.SearchRepository

class PerformSearchUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String): Result<List<SearchResult>> {
        // Regra de negócio: não buscar se a query tiver menos de 3 caracteres
        if (query.length < 3) {
            return Result.success(emptyList())
        }
        return searchRepository.search(query)
    }
}