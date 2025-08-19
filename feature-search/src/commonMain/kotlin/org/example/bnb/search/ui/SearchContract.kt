package org.example.bnb.search.ui

import org.example.bnb.search.domain.model.SearchResult

data class SearchState(
    val searchQuery: String = "",
    val searchResults: List<SearchResult> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface SearchEvent {
    data class OnQueryChange(val query: String) : SearchEvent
}