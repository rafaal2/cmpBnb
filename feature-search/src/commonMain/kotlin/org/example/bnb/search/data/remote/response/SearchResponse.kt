package org.example.bnb.search.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    val results: List<SearchResultDto>
)