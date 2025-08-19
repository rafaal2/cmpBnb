package org.example.bnb.search.domain.repository

import org.example.bnb.search.domain.model.SearchResult

interface SearchRepository {
    suspend fun search(query: String): Result<List<SearchResult>>
}